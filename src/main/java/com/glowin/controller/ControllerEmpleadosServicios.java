package com.glowin.controller;


import com.glowin.models.Empleado;
import com.glowin.models.Input.EmpleadoServicioInput;
import com.glowin.models.Servicio;
import com.glowin.repository.IEmpleadoRepository;
import com.glowin.repository.IServicioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;


@RestController
@RequestMapping("/empleados-servicios")
public class ControllerEmpleadosServicios {

    @Autowired
    private IEmpleadoRepository empleadoRepository;

    @Autowired
    private IServicioRepository servicioRepository;

    @Operation(summary = "Obtener IDs de servicios de un empleado dado su iD", description = "Recupera todos los IDs de los servicios que ofrece un empleado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "IDs de servicios encontrados"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    @GetMapping("/empleado/{idEmpleado}/servicios")
    public ResponseEntity<List<Long>> getServicioIdsByEmpleado(
            @Parameter(description = "ID del empleado", required = true) @PathVariable Long idEmpleado) {
        List<Long> servicioIds = empleadoRepository.findServicioIdsByEmpleadoId(idEmpleado);
        if (servicioIds != null && !servicioIds.isEmpty()) {
            return ResponseEntity.ok(servicioIds);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Obtener IDs de empleados que ofrecen un servicio dado su iD", description = "Recupera todos los IDs de los empleados que ofrecen un servicio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "IDs de empleados encontrados"),
            @ApiResponse(responseCode = "404", description = "Servicio no encontrado")
    })
    @GetMapping("/servicio/{idServicio}/empleados")
    public ResponseEntity<List<Long>> getEmpleadoIdsByServicio(
            @Parameter(description = "ID del servicio", required = true) @PathVariable Long idServicio) {
        List<Long> empleadoIds = servicioRepository.findEmpleadoIdsByServicioId(idServicio);
        if (empleadoIds != null && !empleadoIds.isEmpty()) {
            return ResponseEntity.ok(empleadoIds);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}