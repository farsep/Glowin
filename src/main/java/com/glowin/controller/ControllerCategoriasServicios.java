package com.glowin.controller;

import com.glowin.dto.CategoriaServicioDetallesDTO;
import com.glowin.models.CategoriaServicio;
import com.glowin.models.Input.CategoriaServicioInput;
import com.glowin.models.output.CategoriaServicioOutput;
import com.glowin.repository.ICategoriaServicioRepository;
import com.glowin.repository.IServicioRepository;
import com.glowin.service.CategoriaServicioService;
import com.glowin.service.CompartirRedesSocialesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/categorias-servicios")
public class ControllerCategoriasServicios {

    @Autowired
    private ICategoriaServicioRepository categoriaServicioRepository;

    @Autowired
    private IServicioRepository servicioRepository;

    @Autowired
    private CategoriaServicioService categoriaServicioService;

    @Autowired
    private CompartirRedesSocialesService compartirRedesSocialesService;

    @Operation(summary = "Obtener todas las categorías de servicios", description = "Recupera todas las categorías de servicios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categorías encontradas", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "204", description = "No se encontraron categorías", content = @Content)
    })
    @GetMapping("/all")
    public ResponseEntity<List<CategoriaServicioOutput>> getAllCategoriasServicios() {
        List<CategoriaServicioOutput> categoriasServicios = categoriaServicioRepository.findAll()
                .stream()
                .map(CategoriaServicioOutput::new)
                .toList();
        return ResponseEntity.ok(categoriasServicios);
    }

    @Operation(summary = "Obtener categoría por ID", description = "Recupera una categoría de servicio por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría encontrada", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoriaServicio(
            @Parameter(description = "ID de la categoría a recuperar", required = true) @PathVariable Long id) {
        Optional<CategoriaServicio> categoriaServicio = categoriaServicioRepository.findById(id);
        if (categoriaServicio.isPresent()) {
            return ResponseEntity.ok(new CategoriaServicioOutput(categoriaServicio.get()));
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Categoría no encontrada");
            response.put("status", "404");
            response.put("timestamp", LocalDate.now().toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @Operation(summary = "Obtener detalles de la categoría de servicio", description = "Recupera los detalles de una categoría de servicio por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalles de la categoría encontrados", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada", content = @Content)
    })
    @GetMapping("/{id}/detalles")
    public ResponseEntity<CategoriaServicioDetallesDTO> obtenerDetallesCategoriaServicio(
            @Parameter(description = "ID de la categoría a recuperar", required = true) @PathVariable Long id) {
        CategoriaServicioDetallesDTO detalles = categoriaServicioService.obtenerDetallesCategoriaServicio(id);
        return ResponseEntity.ok(detalles);
    }

    @Operation(summary = "Obtener enlaces para compartir la categoría de servicio", description = "Genera enlaces para compartir una categoría de servicio en redes sociales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Enlaces generados", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada", content = @Content)
    })
    @GetMapping("/{id}/compartir")
    public ResponseEntity<Map<String, String>> obtenerEnlacesCompartir(@PathVariable Long id) {
        String enlace = categoriaServicioService.obtenerDetallesCategoriaServicio(id).getEnlace();
        String enlaceFacebook = compartirRedesSocialesService.generarEnlaceCompartirFacebook(enlace);
        String enlaceWhatsApp = compartirRedesSocialesService.generarEnlaceCompartirWhatsApp(enlace);

        Map<String, String> enlaces = new HashMap<>();
        enlaces.put("facebook", enlaceFacebook);
        enlaces.put("whatsapp", enlaceWhatsApp);

        return ResponseEntity.ok(enlaces);
    }

    @Operation(summary = "Actualizar una categoría de servicio", description = "Actualiza los detalles de una categoría de servicio existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría actualizada", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada", content = @Content)
    })
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategoriaServicio(
            @Parameter(description = "ID de la categoría a actualizar", required = true) @PathVariable Long id,
            @Parameter(description = "Detalles actualizados de la categoría", required = true) @Valid @RequestBody CategoriaServicioInput categoriaServicio) {
        Optional<CategoriaServicio> categoriaServicioOptional = categoriaServicioRepository.findById(id);
        if (categoriaServicioOptional.isPresent()) {
            CategoriaServicio categoriaServicioActualizada = categoriaServicioOptional.get();
            categoriaServicioActualizada.setNombre(categoriaServicio.nombre().toUpperCase());
            categoriaServicioActualizada.setUrlImagen(categoriaServicio.urlImagen());
            categoriaServicioRepository.save(categoriaServicioActualizada);
            return ResponseEntity.ok(new CategoriaServicioOutput(categoriaServicioActualizada));
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Categoría no encontrada");
            response.put("status", "404");
            response.put("timestamp", LocalDate.now().toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @Operation(summary = "Registrar una nueva categoría de servicio", description = "Crea una nueva categoría de servicio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoría creada", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content)
    })
    @Transactional
    @PostMapping
    public ResponseEntity<CategoriaServicioOutput> registerCategoriaServicio(
            @Parameter(description = "Datos de entrada de la categoría", required = true) @Valid @RequestBody CategoriaServicioInput categoriaServicio) {
        CategoriaServicio nuevaCategoria = new CategoriaServicio(
                new CategoriaServicioInput(categoriaServicio.nombre().toUpperCase(), categoriaServicio.urlImagen())
        );
        categoriaServicioRepository.save(nuevaCategoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CategoriaServicioOutput(nuevaCategoria));
    }

    @Operation(summary = "Eliminar una categoría de servicio", description = "Elimina una categoría de servicio por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría y todos los servicios asociados eliminados", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteCategoriaServicio(
            @Parameter(description = "ID de la categoría a eliminar", required = true) @PathVariable Long id) {
        Optional<CategoriaServicio> categoriaServicioOpt = categoriaServicioRepository.findById(id);
        if (categoriaServicioOpt.isPresent()) {
            categoriaServicioRepository.deleteById(id);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Categoría y todos los servicios asociados eliminados con éxito");
            response.put("status", "200");
            response.put("timestamp", LocalDate.now().toString());
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Categoría no encontrada");
            response.put("status", "404");
            response.put("timestamp", LocalDate.now().toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}