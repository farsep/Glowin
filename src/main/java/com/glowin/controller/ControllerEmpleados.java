package com.glowin.controller;

import com.glowin.models.Empleado;
import com.glowin.models.Input.EmpleadoInput;
import com.glowin.models.Update.EmpleadoUpdate;
import com.glowin.models.output.EmpleadoOutput;
import com.glowin.repository.IEmpleadoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "Obtener empleado por ID", description = "Recupera un empleado por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado encontrado"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoOutput> getEmpleado(
            @Parameter(description = "ID del empleado a recuperar", required = true) @PathVariable Long id) {
        Optional<Empleado> empleado = empleadoRepository.findById(id);
        if (empleado.isPresent()) {
            return ResponseEntity.ok(new EmpleadoOutput(empleado.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Obtener todos los empleados", description = "Recupera todos los empleados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleados encontrados"),
            @ApiResponse(responseCode = "204", description = "No se encontraron empleados")
    })
    @GetMapping("/all")
    public ResponseEntity<List<EmpleadoOutput>> getAllEmpleados() {
        List<Empleado> empleados = empleadoRepository.findAll();
        List<EmpleadoOutput> empleadoOutputs = empleados.stream().map(EmpleadoOutput::new).toList();
        return ResponseEntity.ok(empleadoOutputs);
    }

    @Operation(summary = "Registrar un nuevo empleado", description = "Crea un nuevo empleado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empleado creado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @Transactional
    @PostMapping
    public ResponseEntity<?> registerEmpleado(
            @Parameter(description = "Datos de entrada del empleado", required = true) @Valid @RequestBody EmpleadoInput empleadoInput) {
        Empleado empleado = new Empleado(empleadoInput);
        empleadoRepository.save(empleado);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                        .buildAndExpand(empleado.getId()).toUri()).body(new EmpleadoOutput(empleado));
    }

    @Operation(summary = "Actualizar un empleado", description = "Actualiza los datos de un empleado existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado actualizado"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmpleado(
            @Parameter(description = "ID del empleado a actualizar", required = true) @PathVariable Long id,
            @Parameter(description = "Datos de actualización del empleado", required = true) @RequestBody EmpleadoUpdate empleadoUpdate) {
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

    @Operation(summary = "Eliminar un empleado", description = "Elimina un empleado por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado eliminado"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteEmpleado(
            @Parameter(description = "ID del empleado a eliminar", required = true) @PathVariable Long id) {
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
}