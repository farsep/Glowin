package com.glowin.controller;

import com.glowin.models.ImagenServicio;
import com.glowin.models.Input.ImagenServicioInput;
import com.glowin.models.Servicio;
import com.glowin.models.Update.ImagenServicioUpdate;
import com.glowin.models.output.ImagenServicioOutput;
import com.glowin.repository.IImagenServicioRepository;
import com.glowin.repository.IServicioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/servicios/{idServicio}/imagenes")
public class ControllerImagenesServicios {

    @Autowired
    private IImagenServicioRepository imagenServicioRepository;

    @Autowired
    private IServicioRepository servicioRepository;

    @Operation(summary = "Listar todas las imágenes de un servicio", description = "Recupera todas las imágenes de un servicio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imágenes encontradas"),
            @ApiResponse(responseCode = "204", description = "No se encontraron imágenes")
    })
    @GetMapping
    public ResponseEntity<List<ImagenServicioOutput>> getImagenes(
            @Parameter(description = "ID del servicio", required = true) @PathVariable Long idServicio) {
        List<ImagenServicio> imagenes = imagenServicioRepository.findByServicioId(idServicio);
        List<ImagenServicioOutput> dtoList = imagenes.stream()
                .map(ImagenServicioOutput::new)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    @Operation(summary = "Obtener imagen por ID", description = "Recupera una imagen por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagen encontrada"),
            @ApiResponse(responseCode = "404", description = "Imagen no encontrada"),
            @ApiResponse(responseCode = "400", description = "La imagen no corresponde al servicio indicado")
    })
    @GetMapping("/{idImagen}")
    public ResponseEntity<?> getImagen(
            @Parameter(description = "ID del servicio", required = true) @PathVariable Long idServicio,
            @Parameter(description = "ID de la imagen a recuperar", required = true) @PathVariable Long idImagen) {

        Optional<ImagenServicio> optionalImg = imagenServicioRepository.findById(idImagen);
        if (optionalImg.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ImagenServicio img = optionalImg.get();
        if (!img.getServicio().getId().equals(idServicio)) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "La imagen no corresponde al servicio indicado",
                    "status", "400",
                    "timestamp", LocalDate.now().toString()
            ));
        }

        return ResponseEntity.ok(new ImagenServicioOutput(img));
    }

    @Operation(summary = "Crear una nueva imagen para un servicio", description = "Crea una nueva imagen para un servicio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Imagen creada"),
            @ApiResponse(responseCode = "404", description = "Servicio no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<?> createImagen(
            @Parameter(description = "ID del servicio", required = true) @PathVariable Long idServicio,
            @Parameter(description = "Datos de entrada de la imagen", required = true) @Valid @RequestBody ImagenServicioInput input) {

        Servicio servicio = servicioRepository.findById(idServicio)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

        ImagenServicio nuevaImagen = new ImagenServicio();
        nuevaImagen.setTitulo(input.titulo());
        nuevaImagen.setDescripcion(input.descripcion());
        nuevaImagen.setUrlImagen(input.urlImagen());
        nuevaImagen.setFechaCreacion(
                (input.fechaCreacion() != null) ? input.fechaCreacion() : LocalDate.now()
        );
        nuevaImagen.setServicio(servicio);

        imagenServicioRepository.save(nuevaImagen);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ImagenServicioOutput(nuevaImagen));
    }

    @Operation(summary = "Actualizar una imagen", description = "Actualiza los datos de una imagen existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagen actualizada"),
            @ApiResponse(responseCode = "404", description = "Imagen no encontrada"),
            @ApiResponse(responseCode = "400", description = "La imagen no corresponde al servicio indicado")
    })
    @PutMapping("/{idImagen}")
    public ResponseEntity<?> updateImagen(
            @Parameter(description = "ID del servicio", required = true) @PathVariable Long idServicio,
            @Parameter(description = "ID de la imagen a actualizar", required = true) @PathVariable Long idImagen,
            @Parameter(description = "Datos de actualización de la imagen", required = true) @Valid @RequestBody ImagenServicioUpdate update) {
        Optional<ImagenServicio> optionalImg = imagenServicioRepository.findById(idImagen);
        if (optionalImg.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ImagenServicio img = optionalImg.get();
        if (!img.getServicio().getId().equals(idServicio)) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "La imagen no corresponde al servicio indicado",
                    "status", "400",
                    "timestamp", LocalDate.now().toString()
            ));
        }

        if (update.titulo() != null) {
            img.setTitulo(update.titulo());
        }
        if (update.descripcion() != null) {
            img.setDescripcion(update.descripcion());
        }
        if (update.urlImagen() != null) {
            img.setUrlImagen(update.urlImagen());
        }
        if (update.fechaCreacion() != null) {
            img.setFechaCreacion(update.fechaCreacion());
        }
        if (update.idServicio() != null) {
            Servicio nuevoServicio = servicioRepository.findById(update.idServicio())
                    .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
            img.setServicio(nuevoServicio);
        }

        imagenServicioRepository.save(img);
        return ResponseEntity.ok(new ImagenServicioOutput(img));
    }

    @Operation(summary = "Eliminar una imagen", description = "Elimina una imagen por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagen eliminada"),
            @ApiResponse(responseCode = "404", description = "Imagen no encontrada"),
            @ApiResponse(responseCode = "400", description = "La imagen no corresponde al servicio indicado")
    })
    @DeleteMapping("/{idImagen}")
    public ResponseEntity<?> deleteImagen(
            @Parameter(description = "ID del servicio", required = true) @PathVariable Long idServicio,
            @Parameter(description = "ID de la imagen a eliminar", required = true) @PathVariable Long idImagen) {

        Optional<ImagenServicio> optionalImg = imagenServicioRepository.findById(idImagen);
        if (optionalImg.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ImagenServicio img = optionalImg.get();
        if (!img.getServicio().getId().equals(idServicio)) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "La imagen no corresponde al servicio indicado",
                    "status", "400",
                    "timestamp", LocalDate.now().toString()
            ));
        }

        imagenServicioRepository.delete(img);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Imagen eliminada con éxito");
        response.put("status", "200");
        response.put("timestamp", LocalDate.now().toString());

        return ResponseEntity.ok(response);
    }

    @ControllerAdvice
    static class GlobalExceptionHandler {
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
            Map<String, String> errors = new HashMap<>();
            ex.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
    }
}