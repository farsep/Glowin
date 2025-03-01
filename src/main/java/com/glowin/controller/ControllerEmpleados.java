package com.glowin.controller;

import com.glowin.models.Empleado;
import com.glowin.models.Input.EmpleadoInput;
import com.glowin.models.Update.EmpleadoUpdate;
import com.glowin.models.output.EmpleadoOutput;
import com.glowin.repository.IEmpleadoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/empleados")
public class ControllerEmpleados {

    @Autowired
    private IEmpleadoRepository empleadoRepository;

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoOutput> getEmpleado(@PathVariable Long id) {
        Optional<Empleado> empleado = empleadoRepository.findById(id);
        if (empleado.isPresent()) {
            return ResponseEntity.ok(new EmpleadoOutput(empleado.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmpleadoOutput>> getAllEmpleados() {
        List<Empleado> empleados = empleadoRepository.findAll();
        List<EmpleadoOutput> empleadoOutputs = empleados.stream().map(EmpleadoOutput::new).toList();
        return ResponseEntity.ok(empleadoOutputs);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<?> registerEmpleado(@Valid @RequestBody EmpleadoInput empleadoInput) {
        Empleado empleado = new Empleado(empleadoInput);
        empleadoRepository.save(empleado);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                        .buildAndExpand(empleado.getId()).toUri()).body(new EmpleadoOutput(empleado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmpleado(@PathVariable Long id, @RequestBody EmpleadoUpdate empleadoUpdate) {
        Optional<Empleado> optionalEmpleado = empleadoRepository.findById(id);

        if (optionalEmpleado.isPresent()) {
            Empleado empleado = optionalEmpleado.get();
            updateEmpleadoFields(empleado, empleadoUpdate);
            empleadoRepository.save(empleado);
            return ResponseEntity.ok(new EmpleadoOutput(empleado));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteEmpleado(@PathVariable Long id) {
        Optional<Empleado> empleado = empleadoRepository.findById(id);
        if (empleado.isPresent()) {
            empleadoRepository.delete(empleado.get());

            Map<String, String> response = new HashMap<>();
            response.put("message", "Empleado eliminado con éxito");
            response.put("status", "200");
            response.put("timestamp", LocalDate.now().toString());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private void updateEmpleadoFields(Empleado empleado, EmpleadoUpdate empleadoUpdate) {
        if (empleadoUpdate.nombre() != null) empleado.setNombre(empleadoUpdate.nombre());
        if (empleadoUpdate.apellido() != null) empleado.setApellido(empleadoUpdate.apellido());
        if (empleadoUpdate.email() != null) empleado.setEmail(empleadoUpdate.email());
        if (empleadoUpdate.celular() != null) empleado.setCelular(empleadoUpdate.celular());
        if (empleadoUpdate.salario() != null) empleado.setSalario(empleadoUpdate.salario());
        if (empleadoUpdate.dni() != null) empleado.setDni(empleadoUpdate.dni());
        if (empleadoUpdate.fechaRegistro() != null) empleado.setFechaRegistro(empleadoUpdate.fechaRegistro());
        if (empleadoUpdate.tipoJornada() != null) empleado.setTipoJornada(empleadoUpdate.tipoJornada());
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