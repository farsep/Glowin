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
import com.glowin.service.EmailService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private EmailService emailService;

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
            @Parameter(description = "Fecha de inicio en el formato yyyy-MM-dd", required = true) @RequestParam LocalDate fechaInicio,
            @Parameter(description = "Fecha de fin en el formato yyyy-MM-dd", required = true) @RequestParam LocalDate fechaFin,
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
    @Operation(summary = "Registrar una nueva reserva", description = "Crea una nueva reserva y envía un correo de confirmación al usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reserva creada"),
            @ApiResponse(responseCode = "404", description = "Usuario, servicio o empleado no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error al enviar el correo")
    })
    @Transactional
    @PostMapping
    public ResponseEntity<?> registerReserva(@Valid @RequestBody ReservaInput reservaInput) {
        Optional<Usuario> usuarioOpt = usuarioRepo.findById(reservaInput.idCliente());
        Optional<Servicio> servicioOpt = servicioRepo.findById(reservaInput.idServicio());
        Optional<Empleado> empleadoOpt = empleadoRepo.findById(reservaInput.idEmpleado());

        if (usuarioOpt.isEmpty() || servicioOpt.isEmpty() || empleadoOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of(
                    "error", "Usuario, servicio o empleado no encontrado",
                    "status", "404",
                    "timestamp", LocalDate.now().toString()
            ));
        }

        Usuario usuario = usuarioOpt.get();
        Servicio servicio = servicioOpt.get();
        Empleado empleado = empleadoOpt.get();

        Reserva reserva = new Reserva(reservaInput, usuario, servicio, empleado);
        reservaRepo.save(reserva);

        // Construcción del correo electrónico
        String subject = "Confirmación de Reserva en Glowin";
        String emailContent = String.format("""
                <html>
                <body style="font-family: Arial, sans-serif; line-height: 1.6;">
                    <h2>¡Hola %s!</h2>
                    <p>Tu reserva ha sido confirmada con éxito. Aquí están los detalles:</p>
                    <ul>
                        <li><strong>Servicio:</strong> %s</li>
                        <li><strong>Fecha:</strong> %s</li>
                        <li><strong>Hora:</strong> %s</li>
                        <li><strong>Empleado:</strong> %s</li>
                    </ul>
                    <p>Si tienes alguna consulta, no dudes en contactarnos.</p>
                    <p>¡Gracias por elegir Glowin!</p>
                    <br>
                    <p>Saludos,<br>Equipo de Glowin</p>
                </body>
                </html>
                """,
                usuario.getNombre(),
                servicio.getNombre(),
                reserva.getFecha().toString(),
                reserva.getHora().toString(),
                empleado.getNombre()
        );

        // Enviar correo electrónico
        try {
            emailService.sendConfirmationEmail(usuario.getEmail(), subject, emailContent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "error", "Error al enviar el correo",
                    "status", "500",
                    "timestamp", LocalDate.now().toString()
            ));
        }

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}").buildAndExpand(reserva.getId()).toUri())
                .body(Map.of(
                        "message", "Reserva creada y correo enviado con éxito",
                        "reservaId", reserva.getId()
                ));
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

    //Endpoint para retornar las reservas disponibles en formato de listas mostrando en slots de 1 hora los horarios disponibles que solo son de 9 AM a 9PM en un rango de fechas para un servicio
    @Operation(summary = "Obtener reservas disponibles", description = "Recupera las reservas disponibles en un rango de fechas para un servicio específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservas disponibles", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "[{\"fecha\":\"2023-10-01\",\"hora\":\"10:00\"}]"))),
            @ApiResponse(responseCode = "204", description = "No se encontraron reservas disponibles", content = @Content)
    })
    @GetMapping("/available")
    public ResponseEntity<?> getAvailableReservas(
            @RequestParam Long idServicio,
            @RequestParam LocalDate fechaInicio,
            @RequestParam LocalDate fechaFin,
            Pageable pageable) {



        Page<Reserva> reservasPage = reservaRepo.findByFechaBetweenAndServicioId(fechaInicio, fechaFin, idServicio, pageable);
        List<Reserva> reservas = reservasPage.getContent();

        List<Map<String, Object>> availableSlots = new ArrayList<>();

        for (LocalDate date : fechaInicio.datesUntil(fechaFin.plusDays(1)).collect(Collectors.toList())) {
            List<Map<String, Object>> dailySlots = generateDailySlots(date);
            List<Map<String, Object>> reservedSlots = reservas.stream()
                    .filter(reserva -> reserva.getFecha().equals(date))
                    .map(reserva -> {
                        Map<String, Object> slot = new HashMap<>();
                        slot.put("fecha", reserva.getFecha().toString());
                        slot.put("hora", reserva.getHora().toString());
                        return slot;
                    })
                    .collect(Collectors.toList());

            for (Map<String, Object> slot : dailySlots) {
                boolean isReserved = reservedSlots.stream().anyMatch(reservedSlot -> reservedSlot.equals(slot));
                if (!isReserved) {
                    availableSlots.add(slot);
                }
            }
        }

        if (availableSlots.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "No se encontraron reservas disponibles");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }

        return ResponseEntity.ok(availableSlots);
    }

    private List<Map<String, Object>> generateDailySlots(LocalDate date) {
        List<Map<String, Object>> slots = new ArrayList<>();
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(21, 0);

        while (!startTime.isAfter(endTime.minusHours(1))) {
            Map<String, Object> slot = new HashMap<>();
            slot.put("fecha", date.toString());
            slot.put("hora", startTime.toString());
            slots.add(slot);
            startTime = startTime.plusHours(1);
        }

        return slots;
    }


    // Metodo para convertir una entidad Reserva a su representación de salida
    public ReservaOutput ConvertToOutput(Reserva reserva) {
        return new ReservaOutput(reserva, reserva.getEmpleado(), reserva.getCliente(), reserva.getServicio());
    }

}