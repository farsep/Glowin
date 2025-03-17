package com.glowin.controller;

import com.glowin.models.Input.UsuarioInput;
import com.glowin.models.Update.UsuarioUpdate;
import com.glowin.models.Usuario;
import com.glowin.models.enums.Rol;
import com.glowin.models.output.UsuarioOutput;
import com.glowin.repository.IUsuarioRepository;
import com.glowin.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@RequestMapping("/usuarios")
public class ControllerUsuarios {
    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation(summary = "Obtener usuario por ID", description = "Recupera un usuario por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioOutput> getUser(
            @Parameter(description = "ID del usuario a recuperar", required = true) @PathVariable Long id) {
        Optional<Usuario> user = usuarioRepository.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(new UsuarioOutput(user.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Obtener todos los usuarios", description = "Recupera todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuarios encontrados"),
            @ApiResponse(responseCode = "204", description = "No se encontraron usuarios")
    })
    @GetMapping("/all")
    public ResponseEntity<List<UsuarioOutput>> getAllUsers() {
        List<Usuario> users = usuarioRepository.findAll();
        List<UsuarioOutput> userOutputs = users.stream().map(UsuarioOutput::new).toList();
        return ResponseEntity.ok(userOutputs);
    }

    @Operation(summary = "Registrar un nuevo usuario", description = "Crea un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado"),
            @ApiResponse(responseCode = "409", description = "El email ya está en uso o ya existe un SUPER_ADMINISTRADOR"),
            @ApiResponse(responseCode = "500", description = "Error al enviar el correo de confirmación"),
            @ApiResponse(responseCode = "400", description = "Error de validación")
    })
    @Transactional
    @PostMapping
    public ResponseEntity<?> registerUser(
            @Parameter(description = "Datos de entrada del usuario", required = true) @Valid @RequestBody UsuarioInput usuarioInput) {
        if (usuarioRepository.existsByEmail(usuarioInput.email())) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "El email ya está en uso");
            response.put("status", "409");
            response.put("timestamp", LocalDate.now().toString());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        Rol nuevoRol = Rol.fromString(usuarioInput.rol());
        if (nuevoRol == Rol.SUPER_ADMINISTRADOR && usuarioRepository.existsByRol(Rol.SUPER_ADMINISTRADOR)) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Ya existe un usuario con el rol SUPER_ADMINISTRADOR");
            response.put("status", "409");
            response.put("timestamp", LocalDate.now().toString());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        Usuario user = new Usuario(usuarioInput);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usuarioRepository.save(user);

        emailService.logMailProperties();

        String subject = "Registro exitoso en Glowin";
        String text = String.format("""
                <html>
                <body style="font-family: Arial, sans-serif; line-height: 1.6;">
                <p>Estimado/a %s,</p>
                <p>¡Tu registro ha sido exitoso!</p>
                <p>Detalles de tu cuenta:</p>
                <ul>
                    <li>Nombre de usuario: %s</li>
                    <li>Correo electrónico: %s</li>
                </ul>
                <p>Puedes iniciar sesión en tu cuenta utilizando el siguiente enlace:</p>
                <a href="http://localhost/ingresar" style="color: #1a73e8;" target="_blank">Iniciar sesión</a>
                <p>Si no has solicitado este registro, por favor ignora este correo.</p>
                <p>Saludos,<br>Andrés<br>CEO de Glowin</p>
                </body>
                </html>
                """,
                user.getNombre(),
                user.getNombre(),
                user.getEmail()
        );

        emailService.sendConfirmationEmail(
                user.getEmail(),
                subject,
                text
        );

        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                        .buildAndExpand(user.getId()).toUri()
        ).body(new UsuarioOutput(user));
    }

    @Operation(summary = "Actualizar un usuario", description = "Actualiza los datos de un usuario existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "409", description = "El email ya está en uso por otro usuario o ya existe un SUPER_ADMINISTRADOR"),
            @ApiResponse(responseCode = "400", description = "Error de validación")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @Parameter(description = "ID del usuario a actualizar", required = true) @PathVariable Long id,
            @Parameter(description = "Datos de actualización del usuario", required = true) @Valid @RequestBody UsuarioUpdate usuarioUpdate) {
        Optional<Usuario> optionalUser = usuarioRepository.findById(id);

        if (optionalUser.isPresent()) {
            Usuario user = optionalUser.get();

            if (usuarioUpdate.email() != null && !usuarioUpdate.email().equals(user.getEmail())) {
                if (usuarioRepository.existsByEmail(usuarioUpdate.email())) {
                    Map<String, String> response = new HashMap<>();
                    response.put("error", "El email ya está en uso por otro usuario");
                    response.put("status", "409");
                    response.put("timestamp", LocalDate.now().toString());
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
                }
                user.setEmail(usuarioUpdate.email());
            }

            if (usuarioUpdate.rol() != null && usuarioUpdate.rol() == Rol.SUPER_ADMINISTRADOR &&
                    usuarioRepository.existsByRol(Rol.SUPER_ADMINISTRADOR)) {
                Map<String, String> response = new HashMap<>();
                response.put("error", "Ya existe un usuario con el rol SUPER_ADMINISTRADOR");
                response.put("status", "409");
                response.put("timestamp", LocalDate.now().toString());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            if (usuarioUpdate.nombre() != null) user.setNombre(usuarioUpdate.nombre());
            if (usuarioUpdate.apellido() != null) user.setApellido(usuarioUpdate.apellido());
            if (usuarioUpdate.password() != null) user.setPassword(usuarioUpdate.password());
            if (usuarioUpdate.celular() != null) user.setCelular(usuarioUpdate.celular());
            if (usuarioUpdate.fechaRegistro() != null) user.setFechaRegistro(usuarioUpdate.fechaRegistro());
            if (usuarioUpdate.horaRegistro() != null) user.setHoraRegistro(usuarioUpdate.horaRegistro());
            if (usuarioUpdate.rol() != null) user.setRol(usuarioUpdate.rol());

            usuarioRepository.save(user);

            return ResponseEntity.ok(new UsuarioOutput(user));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "409", description = "Debe asignar un nuevo SUPER_ADMINISTRADOR antes de eliminar este usuario")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(
            @Parameter(description = "ID del usuario a eliminar", required = true) @PathVariable Long id,
            @Parameter(description = "ID del nuevo SUPER_ADMINISTRADOR", required = false) @RequestParam(required = false) Long nuevoSuperAdminId) {
        Optional<Usuario> user = usuarioRepository.findById(id);

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuario = user.get();

        if (usuario.getRol() == Rol.SUPER_ADMINISTRADOR) {
            if (nuevoSuperAdminId == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                        "error", "Debes asignar un nuevo SUPER_ADMINISTRADOR antes de eliminar este usuario",
                        "status", "409",
                        "timestamp", LocalDate.now().toString()
                ));
            }

            Optional<Usuario> nuevoAdmin = usuarioRepository.findById(nuevoSuperAdminId);

            if (nuevoAdmin.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "error", "El nuevo SUPER_ADMINISTRADOR no existe",
                        "status", "404",
                        "timestamp", LocalDate.now().toString()
                ));
            }

            Usuario nuevoSuperAdmin = nuevoAdmin.get();

            usuarioRepository.delete(usuario);

            nuevoSuperAdmin.setRol(Rol.SUPER_ADMINISTRADOR);
            usuarioRepository.save(nuevoSuperAdmin);
        } else {
            usuarioRepository.delete(usuario);
        }

        return ResponseEntity.ok(Map.of(
                "message", "Usuario eliminado con éxito",
                "status", "200",
                "timestamp", LocalDate.now().toString()
        ));
    }

}