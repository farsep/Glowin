package com.glowin.controller;

import com.glowin.models.LoginRequest;
import com.glowin.models.Usuario;
import com.glowin.models.Input.ResendEmailRequest;
import com.glowin.repository.IUsuarioRepository;
import com.glowin.security.TokenService;
import com.glowin.service.EmailService; // <-- Asegúrate de importar tu servicio de email
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.glowin.models.enums.Rol.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenService proveedorJwt;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/ingresar")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            // Find the user by email
            Optional<Usuario> usuario = Optional.ofNullable((Usuario) usuarioRepository.findByEmail(request.getEmail()));
            if (usuario.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
            }

            // Check if the user ID is in the specified range
            Long userId = usuario.get().getId();
            boolean isPlainTextPasswordUser = (userId >= 1 && userId <= 8);

            // Compare password
            boolean isPasswordValid = isPlainTextPasswordUser
                    ? request.getPassword().equals(usuario.get().getPassword())
                    : passwordEncoder.matches(request.getPassword(), usuario.get().getPassword());

            if (!isPasswordValid) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
            }

            // Generate JWT token
            String jwt = proveedorJwt.generarToken(usuario.get());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Login exitoso");
            response.put("rol", usuario.get().getRol().toString());
            response.put("token", jwt);

            // URL de redirección según el rol
            switch (usuario.get().getRol()) {
                case SUPER_ADMINISTRADOR -> response.put("redirectUrl", "/dashboard/superadmin");
                case ADMINISTRADOR -> response.put("redirectUrl", "/dashboard/admin");
                case CLIENTE -> response.put("redirectUrl", "/home");
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    @PostMapping("/resend-confirmation")
    public ResponseEntity<?> resendConfirmationEmail(@RequestBody ResendEmailRequest request) {
        // 1. Buscar usuario por email
        Optional<Usuario> optionalUser = Optional.ofNullable((Usuario) usuarioRepository.findByEmail(request.email()));
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un usuario con el correo: " + request.email());
        }

        Usuario user = optionalUser.get();

        // 2. Construir el mensaje de reenvío
        String subject = "Reenvío de confirmación de registro";
        String text = String.format("""
                Hola %s,

                Te reenviamos el correo de confirmación para tu cuenta registrada en Glowin.
                Tus datos:
                - Usuario: %s
                - Correo: %s

                Puedes iniciar sesión aquí: http://localhost:8080/login

                Si no has solicitado este registro, por favor ignora este correo.

                Saludos,
                Andrés CEO de Glowin
                """,
                user.getNombre(), user.getNombre(), user.getEmail());

        // 3. Enviar correo
        emailService.sendConfirmationEmail(user.getEmail(), subject, text);

        // 4. Responder
        return ResponseEntity.ok("Correo de confirmación reenviado a " + request.email());
    }
}
