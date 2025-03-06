package com.glowin.controller;

import com.glowin.models.LoginRequest;
import com.glowin.models.Usuario;
import com.glowin.models.Input.ResendEmailRequest;
import com.glowin.repository.IUsuarioRepository;
import com.glowin.service.EmailService; // <-- Asegúrate de importar tu servicio de email
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Map<String, String> response = new HashMap<>();
        response.put("message", "Login exitoso");
        response.put("rol", usuario.getRol().toString());

        // URL de redirección según el rol
        switch (usuario.getRol()) {
            case SUPER_ADMINISTRADOR -> response.put("redirectUrl", "/dashboard/superadmin");
            case ADMINISTRADOR -> response.put("redirectUrl", "/dashboard/admin");
            case CLIENTE -> response.put("redirectUrl", "/home");
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/resend-confirmation")
    public ResponseEntity<?> resendConfirmationEmail(@RequestBody ResendEmailRequest request) {
        // 1. Buscar usuario por email
        Optional<Usuario> optionalUser = usuarioRepository.findByEmail(request.email());
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
