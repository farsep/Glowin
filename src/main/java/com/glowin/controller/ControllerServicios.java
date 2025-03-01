package com.glowin.controller;

import com.glowin.models.Input.ServicioInput;
import com.glowin.models.Servicio;
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

import java.util.HashMap;
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

    @Transactional
    @PostMapping
    public ResponseEntity<ServicioOutput> registerServicio(@Valid @RequestBody ServicioInput servicioInput) {
        Servicio servicio = new Servicio(servicioInput, categoriaServicioRepository.findById(servicioInput.categoriaId()).orElseThrow());
        servicioRepository.save(servicio);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                        .buildAndExpand(servicio.getId()).toUri()).body(new ServicioOutput(servicio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteServicio(@PathVariable Long id) {
        Optional<Servicio> servicio = servicioRepository.findById(id);
        if (servicio.isPresent()) {
            servicioRepository.delete(servicio.get());
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("message", "Service deleted successfully");
            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", "application/json");
            return ResponseEntity.ok().headers(header).body(jsonObject.toString());
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