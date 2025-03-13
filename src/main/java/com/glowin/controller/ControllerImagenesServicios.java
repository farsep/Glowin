package com.glowin.controller;

import com.glowin.models.ImagenServicio;
import com.glowin.models.Input.ImagenServicioInput;
import com.glowin.models.Servicio;
import com.glowin.models.Update.ImagenServicioUpdate;
import com.glowin.models.output.ImagenServicioOutput;
import com.glowin.repository.IImagenServicioRepository;
import com.glowin.repository.IServicioRepository;
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
@RequestMapping("/imagenes-servicios/{idServicio}/imagenes")
public class ControllerImagenesServicios {

    @Autowired
    private IImagenServicioRepository imagenServicioRepository;

    @Autowired
    private IServicioRepository servicioRepository;

    // 1. Listar todas las imágenes de un servicio
    @GetMapping
    public ResponseEntity<List<ImagenServicioOutput>> getImagenes(@PathVariable Long idServicio) {
        List<ImagenServicio> imagenes = imagenServicioRepository.findByServicioId(idServicio);
        List<ImagenServicioOutput> dtoList = imagenes.stream()
                .map(ImagenServicioOutput::new)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    // 2. Obtener imagen por ID
    @GetMapping("/{idImagen}")
    public ResponseEntity<?> getImagen(
            @PathVariable Long idServicio,
            @PathVariable Long idImagen) {

        Optional<ImagenServicio> optionalImg = imagenServicioRepository.findById(idImagen);
        if (optionalImg.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ImagenServicio img = optionalImg.get();
        // Verificamos que la imagen pertenezca al servicio correcto
        if (!img.getServicio().getId().equals(idServicio)) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "La imagen no corresponde al servicio indicado",
                    "status", "400",
                    "timestamp", LocalDate.now().toString()
            ));
        }

        return ResponseEntity.ok(new ImagenServicioOutput(img));
    }

    // 3. Crear una nueva imagen para un servicio
    @PostMapping
    public ResponseEntity<?> createImagen(
            @PathVariable Long idServicio,
            @Valid @RequestBody ImagenServicioInput input) {

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

    // 4. Actualizar una imagen
    @PutMapping("/{idImagen}")
    public ResponseEntity<?> updateImagen(
            @PathVariable Long idServicio,
            @PathVariable Long idImagen,
            @Valid @RequestBody ImagenServicioUpdate update
    ) {
        Optional<ImagenServicio> optionalImg = imagenServicioRepository.findById(idImagen);
        if (optionalImg.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ImagenServicio img = optionalImg.get();
        // Verificamos que la imagen corresponda al servicio
        if (!img.getServicio().getId().equals(idServicio)) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "La imagen no corresponde al servicio indicado",
                    "status", "400",
                    "timestamp", LocalDate.now().toString()
            ));
        }

        // Actualizamos solo campos no nulos
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
            // Si deseas permitir cambiar la imagen de servicio
            Servicio nuevoServicio = servicioRepository.findById(update.idServicio())
                    .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
            img.setServicio(nuevoServicio);
        }

        imagenServicioRepository.save(img);
        return ResponseEntity.ok(new ImagenServicioOutput(img));
    }

    // 5. Eliminar una imagen
    @DeleteMapping("/{idImagen}")
    public ResponseEntity<?> deleteImagen(
            @PathVariable Long idServicio,
            @PathVariable Long idImagen) {

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

    // Manejo de validaciones: estilo similar a ControllerServicios
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
