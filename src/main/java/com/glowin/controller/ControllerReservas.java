package com.glowin.controller;

import com.glowin.models.Empleado;
import com.glowin.models.Input.ReservaInput;
import com.glowin.models.Reserva;
import com.glowin.models.Servicio;
import com.glowin.models.Usuario;
import com.glowin.models.output.ReservaOutput;
import com.glowin.repository.IEmpleadoRepository;
import com.glowin.repository.IReservaRepository;
import com.glowin.repository.IServicioRepository;
import com.glowin.repository.IUsuarioRepository;
import com.google.gson.JsonObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/reservas")
public class ControllerReservas {

    @Autowired
    private IReservaRepository reservaRepo;

    @Autowired
    private IEmpleadoRepository empleadoRepo;

    @Autowired
    private IUsuarioRepository usuarioRepo;

    @Autowired
    private IServicioRepository servicioRepo;

    @Operation(summary = "Get all reservations", description = "Retrieve all reservations with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the reservations", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "204", description = "No reservations found", content = @Content)
    })
    @GetMapping("/all")
    public ResponseEntity<Page<ReservaOutput>> getAllReservas(Pageable pageable) {
        Page<ReservaOutput> reservas = reservaRepo.findAll(pageable).map(this::ConvertToOutput);
        return ResponseEntity.ok(reservas);
    }

    @Operation(summary = "Get reservation by ID", description = "Retrieve a reservation by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the reservation", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "204", description = "No reservation found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReservaOutput> getReserva(
            @Parameter(description = "ID of the reservation to be retrieved", required = true) @PathVariable Long id) {
        Optional<Reserva> reserva = reservaRepo.findById(id);
        return reserva.map(value -> ResponseEntity.ok(ConvertToOutput(value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    @Operation(summary = "Get reservations by employee ID", description = "Retrieve reservations for a specific employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the reservations", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "204", description = "No reservations found", content = @Content)
    })
    @GetMapping("/employee/{id}")
    public ResponseEntity<?> getReservasByEmployee(
            @Parameter(description = "ID of the employee", required = true) @PathVariable Long id, Pageable pageable) {
        Page<ReservaOutput> reservas = reservaRepo.findByEmpleadoId(id, pageable).map(this::ConvertToOutput);
        if (reservas.isEmpty()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("message", "No reservations found for the specified employee");
            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", "application/json");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).headers(header).body(jsonObject.toString());
        }
        return ResponseEntity.ok(reservas);
    }

    @Operation(summary = "Get reservations by service ID", description = "Retrieve reservations for a specific service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the reservations", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "204", description = "No reservations found", content = @Content)
    })
    @GetMapping("/service/{id}")
    public ResponseEntity<?> getReservasByService(
            @Parameter(description = "ID of the service", required = true) @PathVariable Long id, Pageable pageable) {
        Page<ReservaOutput> reservas = reservaRepo.findByServicioId(id, pageable).map(this::ConvertToOutput);
        if (reservas.isEmpty()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("message", "No reservations found for the specified service");
            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", "application/json");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).headers(header).body(jsonObject.toString());
        }
        return ResponseEntity.ok(reservas);
    }

    @Operation(summary = "Get reservations by date range", description = "Retrieve reservations within a specific date range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the reservations", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "204", description = "No reservations found", content = @Content)
    })
    @GetMapping("/date")
    public ResponseEntity<?> getReservasByDate(
            @Parameter(description = "Start date in the format yyyy-MM-dd", required = true) @RequestParam String fechaInicio,
            @Parameter(description = "End date in the format yyyy-MM-dd", required = true) @RequestParam String fechaFin,
            Pageable pageable) {
        Page<ReservaOutput> reservas = reservaRepo.findByFechaBetween(fechaInicio, fechaFin, pageable).map(this::ConvertToOutput);
        if (reservas.isEmpty()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("message", "No reservations found for the specified date range");
            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", "application/json");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).headers(header).body(jsonObject.toString());
        }
        return ResponseEntity.ok(reservas);
    }

    @Operation(summary = "Get reservations by user ID", description = "Retrieve reservations for a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the reservations", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "204", description = "No reservations found", content = @Content)
    })
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getReservasByUser(
            @Parameter(description = "ID of the user", required = true) @PathVariable Long id, Pageable pageable) {
        Page<ReservaOutput> reservas = reservaRepo.findByClienteId(id, pageable).map(this::ConvertToOutput);
        if (reservas.isEmpty()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("message", "No reservations found for the specified user");
            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", "application/json");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).headers(header).body(jsonObject.toString());
        }
        return ResponseEntity.ok(reservas);
    }

    @Operation(summary = "Register a new reservation", description = "Create a new reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reservation created", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "User, service, or employee not found", content = @Content)
    })
    @Transactional
    @PostMapping
    public ResponseEntity<ReservaOutput> registerReserva(
            @Parameter(description = "Reservation input data", required = true) @Valid @RequestBody ReservaInput reserva) {
        Optional<Usuario> usuario = usuarioRepo.findById(reserva.idCliente());
        Optional<Servicio> servicio = servicioRepo.findById(reserva.idServicio());
        Optional<Empleado> empleado = empleadoRepo.findById(reserva.idEmpleado());
        if (usuario.isPresent() && servicio.isPresent() && empleado.isPresent()) {
            Reserva reserva1 = new Reserva(reserva, usuario.get(), servicio.get(), empleado.get());
            reservaRepo.save(reserva1);
            return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                            .buildAndExpand(reserva1.getId()).toUri()).body(ConvertToOutput(reserva1));
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete a reservation", description = "Delete a reservation by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation deleted", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Reservation not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReserva(
            @Parameter(description = "ID of the reservation to be deleted", required = true) @PathVariable Long id) {
        Optional<Reserva> reserva = reservaRepo.findById(id);
        if (reserva.isPresent()) {
            reservaRepo.delete(reserva.get());
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("message", "Reservation deleted successfully");
            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", "application/json");
            return ResponseEntity.ok().headers(header).body(jsonObject.toString());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ReservaOutput ConvertToOutput(Reserva reserva) {
        return new ReservaOutput(reserva, reserva.getEmpleado(), reserva.getCliente(), reserva.getServicio());
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