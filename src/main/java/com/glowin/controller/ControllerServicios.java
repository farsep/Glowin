package com.glowin.controller;

import com.glowin.models.CategoriaServicio;
import com.glowin.models.Input.ServicioInput;
import com.glowin.models.Servicio;
import com.glowin.models.Update.ServicioUpdate;
import com.glowin.models.output.ServicioOutput;
import com.glowin.repository.ICategoriaServicioRepository;
import com.glowin.repository.IServicioRepository;
import com.google.gson.JsonObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/servicios")
public class ControllerServicios {

    @Autowired
    private IServicioRepository servicioRepository;

    @Autowired
    private ICategoriaServicioRepository categoriaServicioRepository;

    @Operation(summary = "Obtener servicio por ID", description = "Recupera un servicio por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Servicio encontrado"),
            @ApiResponse(responseCode = "404", description = "Servicio no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ServicioOutput> getServicio(
            @Parameter(description = "ID del servicio a recuperar", required = true) @PathVariable Long id) {
        Optional<Servicio> servicio = servicioRepository.findById(id);
        if (servicio.isPresent()) {
            return ResponseEntity.ok(new ServicioOutput(servicio.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Obtener todos los servicios", description = "Recupera todos los servicios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Servicios encontrados"),
            @ApiResponse(responseCode = "204", description = "No se encontraron servicios")
    })
    @GetMapping("/all")
    public ResponseEntity<List<ServicioOutput>> getAllServicios() {
        List<Servicio> servicios = servicioRepository.findAll();
        List<ServicioOutput> servicioOutputs = servicios.stream().map(ServicioOutput::new).toList();
        return ResponseEntity.ok(servicioOutputs);
    }

    @Operation(summary = "Registrar un nuevo servicio", description = "Crea un nuevo servicio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Servicio creado"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    @Transactional
    @PostMapping
    public ResponseEntity<ServicioOutput> registerServicio(
            @Parameter(description = "Datos de entrada del servicio", required = true) @Valid @RequestBody ServicioInput servicioInput) {
        CategoriaServicio categoria;

        if (servicioInput.categoriaId() != null) {
            categoria = categoriaServicioRepository.findById(servicioInput.categoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        } else if (servicioInput.nombreCategoria() != null && !servicioInput.nombreCategoria().isBlank()) {
            categoria = categoriaServicioRepository.findByNombre(servicioInput.nombreCategoria())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        } else {
            throw new RuntimeException("Debe proporcionar un categoriaId o el nombre de una categoría existente");
        }

        Servicio servicio = new Servicio(servicioInput, categoria);
        servicioRepository.save(servicio);

        return ResponseEntity.created(
                        ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                                .buildAndExpand(servicio.getId()).toUri())
                .body(new ServicioOutput(servicio));
    }

    @Operation(summary = "Actualizar un servicio", description = "Actualiza los datos de un servicio existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Servicio actualizado"),
            @ApiResponse(responseCode = "404", description = "Servicio no encontrado"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateServicio(
            @Parameter(description = "ID del servicio a actualizar", required = true) @PathVariable Long id,
            @Parameter(description = "Datos de actualización del servicio", required = true) @Valid @RequestBody ServicioUpdate servicioUpdate) {
        Optional<Servicio> optionalServicio = servicioRepository.findById(id);

        if (optionalServicio.isPresent()) {
            Servicio servicio = optionalServicio.get();

            if (servicioUpdate.nombre() != null) {
                servicio.setNombre(servicioUpdate.nombre());
            }
            if (servicioUpdate.descripcion() != null) {
                servicio.setDescripcion(servicioUpdate.descripcion());
            }
            if (servicioUpdate.duracionMinutos() != null) {
                servicio.setDuracionMinutos(servicioUpdate.duracionMinutos());
            }
            if (servicioUpdate.costo() != null) {
                servicio.setCosto(servicioUpdate.costo());
            }
            if (servicioUpdate.cantidadSesiones() != null) {
                servicio.setCantidadSesiones(servicioUpdate.cantidadSesiones());
            }

            if (servicioUpdate.categoriaId() != null) {
                CategoriaServicio nuevaCategoria = categoriaServicioRepository.findById(servicioUpdate.categoriaId())
                        .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
                servicio.setCategoria(nuevaCategoria);
                servicio.setNombreCategoria(nuevaCategoria.getNombre());
            } else if (servicioUpdate.nombreCategoria() != null && !servicioUpdate.nombreCategoria().isBlank()) {
                CategoriaServicio nuevaCategoria = categoriaServicioRepository.findByNombre(servicioUpdate.nombreCategoria())
                        .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
                servicio.setCategoria(nuevaCategoria);
                servicio.setNombreCategoria(nuevaCategoria.getNombre());
            }

            servicioRepository.save(servicio);

            return ResponseEntity.ok(new ServicioOutput(servicio));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar un servicio", description = "Elimina un servicio por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Servicio eliminado"),
            @ApiResponse(responseCode = "404", description = "Servicio no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteServicio(
            @Parameter(description = "ID del servicio a eliminar", required = true) @PathVariable Long id) {
        Optional<Servicio> servicio = servicioRepository.findById(id);
        if (servicio.isPresent()) {
            servicioRepository.delete(servicio.get());

            Map<String, String> response = new HashMap<>();
            response.put("message", "Servicio eliminado con éxito");
            response.put("status", "200");
            response.put("timestamp", LocalDate.now().toString());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ControllerAdvice
    public class GlobalExceptionHandler {
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