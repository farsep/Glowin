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
import io.swagger.v3.oas.annotations.media.ExampleObject;
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

import java.time.LocalDate;
import java.time.LocalTime;
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

    // Operación para obtener todas las reservas con paginación
    @Operation(summary = "Obtener todas las reservas", description = "Recupera todas las reservas con paginación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservas encontradas", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "[{\"id\":1,\"fecha\":\"2023-10-01\",\"hora\":\"10:00\",\"empleado\":{\"id\":1,\"nombre\":\"Juan\"},\"cliente\":{\"id\":1,\"nombre\":\"Pedro\"},\"servicio\":{\"id\":1,\"nombre\":\"Corte de pelo\"}}]"))),
            @ApiResponse(responseCode = "204", description = "No se encontraron reservas", content = @Content)
    })
    @GetMapping("/all")
    public ResponseEntity<Page<ReservaOutput>> getAllReservas(Pageable pageable) {
        Page<ReservaOutput> reservas = reservaRepo.findAll(pageable).map(this::ConvertToOutput);
        return ResponseEntity.ok(reservas);
    }

    // Operación para obtener una reserva por su ID
    @Operation(summary = "Obtener reserva por ID", description = "Recupera una reserva por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva encontrada", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"id\":1,\"fecha\":\"2023-10-01\",\"hora\":\"10:00\",\"empleado\":{\"id\":1,\"nombre\":\"Juan\"},\"cliente\":{\"id\":1,\"nombre\":\"Pedro\"},\"servicio\":{\"id\":1,\"nombre\":\"Corte de pelo\"}}"))),
            @ApiResponse(responseCode = "204", description = "No se encontró la reserva", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getReserva(
            @Parameter(description = "ID de la reserva a recuperar", required = true) @PathVariable Long id) {
        Optional<Reserva> reserva = reservaRepo.findById(id);
        if (reserva.isPresent()) {
            return ResponseEntity.ok(ConvertToOutput(reserva.get()));
        } else {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("message", "No se encontró la reserva");
            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", "application/json");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).headers(header).body(jsonObject.toString());        }
    }

    // Operación para obtener reservas por ID de empleado
    @Operation(summary = "Obtener reservas por ID de empleado", description = "Recupera reservas para un empleado específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservas encontradas", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "[{\"id\":1,\"fecha\":\"2023-10-01\",\"hora\":\"10:00\",\"empleado\":{\"id\":1,\"nombre\":\"Juan\"},\"cliente\":{\"id\":1,\"nombre\":\"Pedro\"},\"servicio\":{\"id\":1,\"nombre\":\"Corte de pelo\"}}]"))),
            @ApiResponse(responseCode = "204", description = "No se encontraron reservas", content = @Content)
    })
    @GetMapping("/employee/{id}")
    public ResponseEntity<?> getReservasByEmployee(
            @Parameter(description = "ID del empleado", required = true) @PathVariable Long id, Pageable pageable) {
        Page<ReservaOutput> reservas = reservaRepo.findByEmpleadoId(id, pageable).map(this::ConvertToOutput);
        if (reservas.isEmpty()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("message", "No se encontraron reservas para el empleado especificado");
            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", "application/json");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).headers(header).body(jsonObject.toString());
        }
        return ResponseEntity.ok(reservas);
    }

    // Operación para obtener reservas por ID de servicio
    @Operation(summary = "Obtener reservas por ID de servicio", description = "Recupera reservas para un servicio específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservas encontradas", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "[{\"id\":1,\"fecha\":\"2023-10-01\",\"hora\":\"10:00\",\"empleado\":{\"id\":1,\"nombre\":\"Juan\"},\"cliente\":{\"id\":1,\"nombre\":\"Pedro\"},\"servicio\":{\"id\":1,\"nombre\":\"Corte de pelo\"}}]"))),
            @ApiResponse(responseCode = "204", description = "No se encontraron reservas", content = @Content)
    })
    @GetMapping("/service/{id}")
    public ResponseEntity<?> getReservasByService(
            @Parameter(description = "ID del servicio", required = true) @PathVariable Long id, Pageable pageable) {
        Page<ReservaOutput> reservas = reservaRepo.findByServicioId(id, pageable).map(this::ConvertToOutput);
        if (reservas.isEmpty()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("message", "No se encontraron reservas para el servicio especificado");
            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", "application/json");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).headers(header).body(jsonObject.toString());
        }
        return ResponseEntity.ok(reservas);
    }

    // Operación para obtener reservas por rango de fechas
    @Operation(summary = "Obtener reservas por rango de fechas", description = "Recupera reservas dentro de un rango de fechas específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservas encontradas", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "[{\"id\":1,\"fecha\":\"2023-10-01\",\"hora\":\"10:00\",\"empleado\":{\"id\":1,\"nombre\":\"Juan\"},\"cliente\":{\"id\":1,\"nombre\":\"Pedro\"},\"servicio\":{\"id\":1,\"nombre\":\"Corte de pelo\"}}]"))),
            @ApiResponse(responseCode = "204", description = "No se encontraron reservas", content = @Content)
    })
    @GetMapping("/date")
    public ResponseEntity<?> getReservasByDate(
            @Parameter(description = "Fecha de inicio en el formato yyyy-MM-dd", required = true) @RequestParam String fechaInicio,
            @Parameter(description = "Fecha de fin en el formato yyyy-MM-dd", required = true) @RequestParam String fechaFin,
            Pageable pageable) {
        Page<ReservaOutput> reservas = reservaRepo.findByFechaBetween(fechaInicio, fechaFin, pageable).map(this::ConvertToOutput);
        if (reservas.isEmpty()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("message", "No se encontraron reservas para el rango de fechas especificado");
            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", "application/json");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).headers(header).body(jsonObject.toString());
        }
        return ResponseEntity.ok(reservas);
    }

    // Operación para obtener reservas por ID de usuario
    @Operation(summary = "Obtener reservas por ID de usuario", description = "Recupera reservas para un usuario específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservas encontradas", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "[{\"id\":1,\"fecha\":\"2023-10-01\",\"hora\":\"10:00\",\"empleado\":{\"id\":1,\"nombre\":\"Juan\"},\"cliente\":{\"id\":1,\"nombre\":\"Pedro\"},\"servicio\":{\"id\":1,\"nombre\":\"Corte de pelo\"}}]"))),
            @ApiResponse(responseCode = "204", description = "No se encontraron reservas", content = @Content)
    })
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getReservasByUser(
            @Parameter(description = "ID del usuario", required = true) @PathVariable Long id, Pageable pageable) {
        Page<ReservaOutput> reservas = reservaRepo.findByClienteId(id, pageable).map(this::ConvertToOutput);
        if (reservas.isEmpty()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("message", "No se encontraron reservas para el usuario especificado");
            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", "application/json");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).headers(header).body(jsonObject.toString());
        }
        return ResponseEntity.ok(reservas);
    }

    // Operación para registrar una nueva reserva
    /**
     * Registra una nueva reserva.
     *
     * @param reserva Los datos de entrada para la reserva, incluyendo:
     *                - idCliente: ID del cliente (Long, no nulo)
     *                - idServicio: ID del servicio (Long, no nulo)
     *                - idEmpleado: ID del empleado (Long, no nulo)
     *                - fecha: Fecha de la reserva en el formato "yyyy-MM-dd" (String, no vacío)
     *                - hora: Hora de la reserva en el formato "HH:mm:ss" (String, no vacío)
     *                - estado: Estado de la reserva (String, no vacío)
     * @return ResponseEntity con la reserva creada o un mensaje de error si no se encuentra el usuario, servicio o empleado.
     */
    @Operation(summary = "Registrar una nueva reserva", description = "Crea una nueva reserva con los detalles proporcionados. El cuerpo de la solicitud debe tener el siguiente formato:\n{\n  \"idCliente\": 1,\n  \"idServicio\": 1,\n  \"idEmpleado\": 1,\n  \"fecha\": \"yyyy-MM-dd\",\n  \"hora\": \"HH:mm:ss\",\n  \"estado\": \"CONFIRMADA\"\n}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reserva creada", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"id\":1,\"fecha\":\"2023-10-01\",\"hora\":\"10:00:00\",\"empleado\":{\"id\":1,\"nombre\":\"Juan\"},\"cliente\":{\"id\":1,\"nombre\":\"Pedro\"},\"servicio\":{\"id\":1,\"nombre\":\"Corte de pelo\"}}"))),
            @ApiResponse(responseCode = "404", description = "Usuario, servicio o empleado no encontrado", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"error\":\"Usuario, servicio o empleado no encontrado\",\"status\":\"404\",\"timestamp\":\"2023-10-01\"}")))
    })
    @Transactional
    @PostMapping
    public ResponseEntity<?> registerReserva(
            @Parameter(description = "Datos de entrada de la reserva", required = true) @Valid @RequestBody ReservaInput reserva) {
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
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("error", "Usuario, servicio o empleado no encontrado");
        jsonObject.addProperty("status", "404");
        jsonObject.addProperty("timestamp", LocalDate.now().toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonObject.toString());
    }

    // Operación para actualizar una reserva por su ID
    @Operation(summary = "Actualizar una reserva", description = "Actualiza los datos de una reserva existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva actualizada", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"id\":1,\"fecha\":\"2023-10-01\",\"hora\":\"10:00\",\"empleado\":{\"id\":1,\"nombre\":\"Juan\"},\"cliente\":{\"id\":1,\"nombre\":\"Pedro\"},\"servicio\":{\"id\":1,\"nombre\":\"Corte de pelo\"}}"))),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"error\":\"Reserva no encontrada\",\"status\":\"404\",\"timestamp\":\"2023-10-01\"}")))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReserva(
            @Parameter(description = "ID de la reserva a actualizar", required = true) @PathVariable Long id,
            @Parameter(description = "Datos de actualización de la reserva", required = true) @Valid @RequestBody ReservaInput reservaInput) {
        Optional<Reserva> reservaOptional = reservaRepo.findById(id);
        if (reservaOptional.isEmpty()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("error", "Reserva no encontrada");
            jsonObject.addProperty("status", "404");
            jsonObject.addProperty("timestamp", LocalDate.now().toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonObject.toString());
        }

        Reserva reserva = reservaOptional.get();
        Optional<Usuario> usuario = usuarioRepo.findById(reservaInput.idCliente());
        Optional<Servicio> servicio = servicioRepo.findById(reservaInput.idServicio());
        Optional<Empleado> empleado = empleadoRepo.findById(reservaInput.idEmpleado());
        if (usuario.isPresent() && servicio.isPresent() && empleado.isPresent()) {
            reserva.setCliente(usuario.get());
            reserva.setServicio(servicio.get());
            reserva.setEmpleado(empleado.get());
            reserva.setFecha(LocalDate.parse(reservaInput.fecha()));
            reserva.setHora(LocalTime.parse(reservaInput.hora()));
            reservaRepo.save(reserva);
            return ResponseEntity.ok(ConvertToOutput(reserva));
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("error", "Usuario, servicio o empleado no encontrado");
        jsonObject.addProperty("status", "404");
        jsonObject.addProperty("timestamp", LocalDate.now().toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonObject.toString());
    }

    // Operación para eliminar una reserva por su ID
    @Operation(summary = "Eliminar una reserva", description = "Elimina una reserva por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva eliminada", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\":\"Reserva eliminada con éxito\",\"status\":\"200\",\"timestamp\":\"2023-10-01\"}"))),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"error\":\"Reserva no encontrada\",\"status\":\"404\",\"timestamp\":\"2023-10-01\"}")))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReserva(
            @Parameter(description = "ID de la reserva a eliminar", required = true) @PathVariable Long id) {
        Optional<Reserva> reserva = reservaRepo.findById(id);
        if (reserva.isPresent()) {
            reservaRepo.delete(reserva.get());
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("message", "Reserva eliminada con éxito");
            jsonObject.addProperty("status", "200");
            jsonObject.addProperty("timestamp", LocalDate.now().toString());
            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", "application/json");
            return ResponseEntity.ok().headers(header).body(jsonObject.toString());
        } else {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("error", "Reserva no encontrada");
            jsonObject.addProperty("status", "404");
            jsonObject.addProperty("timestamp", LocalDate.now().toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonObject.toString());
        }
    }

/*    //Endpoint para retornar las reservas disponibles en formato de listas  en un rango de fechas para un servicio
    @Operation(summary = "Obtener reservas disponibles", description = "Recupera las reservas disponibles en un rango de fechas para un servicio específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservas disponibles", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "[{\"fecha\":\"2023-10-01\",\"hora\":\"10:00\"}]"))),
            @ApiResponse(responseCode = "204", description = "No se encontraron reservas disponibles", content = @Content)
    })
    @GetMapping("/available")
    public ResponseEntity<?> getAvailableReservas(
            @Parameter(description = "ID del servicio", required = true) @RequestParam Long idServicio,
            @Parameter(description = "Dia de inicio en el formato yyyy-MM-dd", required = true) @RequestParam String fechaInicio,
            @Parameter(description = "Dia de fin en el formato yyyy-MM-dd", required = true) @RequestParam String fechaFin) {
        return ResponseEntity.ok(reservaRepo.findAvailableReservas(idServicio, fechaInicio, fechaFin));
    }*/


    // Metodo para convertir una entidad Reserva a su representación de salida
    public ReservaOutput ConvertToOutput(Reserva reserva) {
        return new ReservaOutput(reserva, reserva.getEmpleado(), reserva.getCliente(), reserva.getServicio());
    }

}