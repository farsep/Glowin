package com.glowin.controller;

import com.glowin.models.CategoriaServicio;
import com.glowin.models.Input.ServicioInput;
import com.glowin.models.Servicio;
import com.glowin.models.Update.ServicioUpdate;
import com.glowin.models.output.ServicioOutput;
import com.glowin.repository.ICategoriaServicioRepository;
import com.glowin.repository.IServicioRepository;
import com.google.gson.JsonObject;
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

    @GetMapping("/{id}")
    public ResponseEntity<ServicioOutput> getServicio(@PathVariable Long id) {
        Optional<Servicio> servicio = servicioRepository.findById(id);
        if (servicio.isPresent()) {
            return ResponseEntity.ok(new ServicioOutput(servicio.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ServicioOutput>> getAllServicios() {
        List<Servicio> servicios = servicioRepository.findAll();
        List<ServicioOutput> servicioOutputs = servicios.stream().map(ServicioOutput::new).toList();
        return ResponseEntity.ok(servicioOutputs);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<ServicioOutput> registerServicio(@Valid @RequestBody ServicioInput servicioInput) {
        CategoriaServicio categoria;

        // Si se envía categoriaId, buscamos la categoría en la BD
        if (servicioInput.categoriaId() != null) {
            categoria = categoriaServicioRepository.findById(servicioInput.categoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        }
        // Si se envía una categoría completa, verificamos si ya existe o la creamos
        else if (servicioInput.categoria() != null) {
            String nombreCategoria = servicioInput.categoria().nombre(); // Usar el getter correcto

            categoria = categoriaServicioRepository.findByNombre(nombreCategoria)
                    .orElseGet(() -> {
                        CategoriaServicio nuevaCategoria = new CategoriaServicio(servicioInput.categoria()); // Convierte el DTO en entidad
                        return categoriaServicioRepository.save(nuevaCategoria);
                    });
        }
        // Si no se envió ni categoriaId ni categoría completa, lanzamos error
        else {
            throw new RuntimeException("Debe proporcionar un categoriaId o una categoría completa");
        }

        Servicio servicio = new Servicio(servicioInput, categoria);
        servicioRepository.save(servicio);

        return ResponseEntity.created(
                        ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                                .buildAndExpand(servicio.getId()).toUri())
                .body(new ServicioOutput(servicio));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateServicio(@PathVariable Long id, @Valid @RequestBody ServicioUpdate servicioUpdate) {
        Optional<Servicio> optionalServicio = servicioRepository.findById(id);

        if (optionalServicio.isPresent()) {
            Servicio servicio = optionalServicio.get();

            // Actualizar solo los valores no nulos
            if (servicioUpdate.nombre() != null) servicio.setNombre(servicioUpdate.nombre());
            if (servicioUpdate.descripcion() != null) servicio.setDescripcion(servicioUpdate.descripcion());
            if (servicioUpdate.duracionMinutos() != null) servicio.setDuracionMinutos(servicioUpdate.duracionMinutos());
            if (servicioUpdate.costo() != null) servicio.setCosto(servicioUpdate.costo());
            if (servicioUpdate.cantidadSesiones() != null) servicio.setCantidadSesiones(servicioUpdate.cantidadSesiones());

            // Manejo correcto de la categoría
            if (servicioUpdate.categoriaId() != null) {
                CategoriaServicio nuevaCategoria = categoriaServicioRepository.findById(servicioUpdate.categoriaId())
                        .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
                servicio.setCategoria(nuevaCategoria);
            }

            servicioRepository.save(servicio);

            return ResponseEntity.ok(new ServicioOutput(servicio));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteServicio(@PathVariable Long id) {
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