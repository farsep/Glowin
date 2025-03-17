package com.glowin.controller;

import com.glowin.models.Empleado;
import com.glowin.models.Input.EmpleadoServicioInput;
import com.glowin.models.Servicio;
import com.glowin.repository.IEmpleadoRepository;
import com.glowin.repository.IServicioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


@RestController
@RequestMapping("/empleados-servicios")
public class ControllerEmpleadosServicios {

    @Autowired
    private IEmpleadoRepository empleadoRepository;

    @Autowired
    private IServicioRepository servicioRepository;

    // ✅ Asignar un servicio a un empleado
    @Operation(summary = "Asignar servicio a empleado", description = "Asigna un servicio a un empleado específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado asignado al servicio correctamente"),
            @ApiResponse(responseCode = "404", description = "Empleado o Servicio no encontrado")
    })
    @PostMapping("/asignar")
    public ResponseEntity<Map<String, String>> asignarEmpleadoAServicio(
            @Valid @RequestBody EmpleadoServicioInput input) {

        Optional<Empleado> empleadoOpt = empleadoRepository.findById(input.idEmpleado());
        Optional<Servicio> servicioOpt = servicioRepository.findById(input.idServicio());

        if (empleadoOpt.isEmpty() || servicioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Empleado o Servicio no encontrado"));
        }

        Empleado empleado = empleadoOpt.get();
        Servicio servicio = servicioOpt.get();

        empleado.getServicios().add(servicio);
        empleadoRepository.save(empleado);

        return ResponseEntity.ok(Map.of("message", "Empleado asignado al servicio correctamente"));
    }

    // ✅ Remover un servicio de un empleado
    @Operation(summary = "Remover servicio de un empleado", description = "Elimina la asignación de un servicio a un empleado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado removido del servicio correctamente"),
            @ApiResponse(responseCode = "404", description = "Empleado o Servicio no encontrado"),
            @ApiResponse(responseCode = "400", description = "El empleado no tiene asignado este servicio")
    })
    @DeleteMapping("/remover")
    public ResponseEntity<Map<String, String>> removerEmpleadoDeServicio(
            @Valid @RequestBody EmpleadoServicioInput input) {

        Optional<Empleado> empleadoOpt = empleadoRepository.findById(input.idEmpleado());
        Optional<Servicio> servicioOpt = servicioRepository.findById(input.idServicio());

        if (empleadoOpt.isEmpty() || servicioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Empleado o Servicio no encontrado"));
        }

        Empleado empleado = empleadoOpt.get();
        Servicio servicio = servicioOpt.get();

        if (!empleado.getServicios().contains(servicio)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "El empleado no tiene asignado este servicio"));
        }

        empleado.getServicios().remove(servicio);
        empleadoRepository.save(empleado);

        return ResponseEntity.ok(Map.of("message", "Empleado removido del servicio correctamente"));
    }

    // ✅ Obtener los servicios de un empleado
    @Operation(summary = "Obtener servicios por empleado", description = "Devuelve todos los servicios asignados a un empleado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Servicios encontrados", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "204", description = "No se encontraron servicios", content = @Content)
    })
    @GetMapping("/empleados/{idEmpleado}/servicios")
    public ResponseEntity<?> obtenerServiciosPorEmpleado(
            @Parameter(description = "ID del empleado", required = true) @PathVariable Long idEmpleado) {

        Optional<Empleado> empleadoOpt = empleadoRepository.findById(idEmpleado);
        if (empleadoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Empleado no encontrado"));
        }

        Set<Servicio> servicios = empleadoOpt.get().getServicios();
        if (servicios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(Map.of("message", "No se encontraron servicios para el empleado"));
        }

        return ResponseEntity.ok(servicios);
    }

    // ✅ Obtener los empleados de un servicio
    @Operation(summary = "Obtener empleados por servicio", description = "Devuelve todos los empleados asignados a un servicio específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleados encontrados", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "204", description = "No se encontraron empleados", content = @Content)
    })
    @GetMapping("/servicios/{idServicio}/empleados")
    public ResponseEntity<?> obtenerEmpleadosPorServicio(
            @Parameter(description = "ID del servicio", required = true) @PathVariable Long idServicio) {

        Optional<Servicio> servicioOpt = servicioRepository.findById(idServicio);
        if (servicioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Servicio no encontrado"));
        }

        Set<Empleado> empleados = servicioOpt.get().getEmpleados();
        if (empleados.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(Map.of("message", "No se encontraron empleados para este servicio"));
        }

        return ResponseEntity.ok(empleados);
    }

    // ✅ Manejador de excepciones global
    @ControllerAdvice
    public static class GlobalExceptionHandler {
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