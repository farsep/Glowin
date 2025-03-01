package com.glowin.controller;

import com.glowin.models.CategoriaServicio;
import com.glowin.models.Input.CategoriaServicioInput;
import com.glowin.models.output.CategoriaServicioOutput;
import com.glowin.repository.ICategoriaServicioRepository;
import com.google.gson.JsonObject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
@RequestMapping("/categorias-servicios")
public class ControllerCategoriasServicios {

    @Autowired
    private ICategoriaServicioRepository categoriaServicioRepository;

    @GetMapping("/all")
    public ResponseEntity<List<CategoriaServicioOutput>> getAllCategoriasServicios() {
        List<CategoriaServicioOutput> categoriasServicios = categoriaServicioRepository.findAll()
                .stream()
                .map(CategoriaServicioOutput::new)
                .toList();
        return ResponseEntity.ok(categoriasServicios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoriaServicio(@PathVariable Long id) {
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

    @Transactional
    @PostMapping
    public ResponseEntity<CategoriaServicioOutput> registerCategoriaServicio(@Valid @RequestBody CategoriaServicioInput categoriaServicio) {
        // Convertir el nombre de la categoría a mayúsculas antes de guardarla
        CategoriaServicio nuevaCategoria = new CategoriaServicio(
                new CategoriaServicioInput(categoriaServicio.nombre().toUpperCase())
        );
        categoriaServicioRepository.save(nuevaCategoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CategoriaServicioOutput(nuevaCategoria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteCategoriaServicio(@PathVariable Long id) {
        Optional<CategoriaServicio> categoriaServicio = categoriaServicioRepository.findById(id);
        if (categoriaServicio.isPresent()) {
            categoriaServicioRepository.delete(categoriaServicio.get());

            Map<String, String> response = new HashMap<>();
            response.put("message", "Categoría eliminada con éxito");
            response.put("status", "200");
            response.put("timestamp", LocalDate.now().toString());

            return ResponseEntity.ok(response);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Categoría no encontrada");
            response.put("status", "404");
            response.put("timestamp", LocalDate.now().toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
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
