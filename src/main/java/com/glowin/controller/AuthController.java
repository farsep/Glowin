package com.glowin.controller;

import com.glowin.models.LoginRequest;
import com.glowin.models.Usuario;
import com.glowin.models.Input.ResendEmailRequest;
import com.glowin.repository.IUsuarioRepository;
import com.glowin.security.TokenService;
import com.glowin.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    @Operation(summary = "Iniciar sesión", description = "Permite a un usuario iniciar sesión con su correo electrónico y contraseña")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Correo electrónico o contraseña inválidos", content = @Content)
    })
    @PostMapping("/ingresar")
    public ResponseEntity<?> login(
            @Parameter(description = "Datos de inicio de sesión", required = true) @RequestBody LoginRequest request,
            HttpServletRequest httpRequest) {
        try {
            Optional<Usuario> usuario = Optional.ofNullable((Usuario) usuarioRepository.findByEmail(request.getEmail()));
            if (usuario.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo electrónico o contraseña inválidos");
            }

            Long userId = usuario.get().getId();
            boolean isPlainTextPasswordUser = (userId >= 1 && userId <= 8);

            boolean isPasswordValid = isPlainTextPasswordUser
                    ? request.getPassword().equals(usuario.get().getPassword())
                    : passwordEncoder.matches(request.getPassword(), usuario.get().getPassword());

            if (!isPasswordValid) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo electrónico o contraseña inválidos");
            }

            String jwt = proveedorJwt.generarToken(usuario.get());
            Map<String, String> response = new HashMap<>();
            String clientIp = httpRequest.getRemoteAddr();
            response.put("IP", clientIp);
            response.put("message", "Login exitoso");
            response.put("rol", usuario.get().getRol().toString());
            response.put("token", jwt);

            switch (usuario.get().getRol()) {
                case SUPER_ADMINISTRADOR -> response.put("redirectUrl", "/dashboard/superadmin");
                case ADMINISTRADOR -> response.put("redirectUrl", "/dashboard/admin");
                case CLIENTE -> response.put("redirectUrl", "/home");
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo electrónico o contraseña inválidos");
        }
    }

    @Operation(summary = "Reenviar correo de confirmación", description = "Reenvía el correo de confirmación de registro a un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Correo de confirmación reenviado", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @PostMapping("/resend-confirmation")
    public ResponseEntity<?> resendConfirmationEmail(
            @Parameter(description = "Datos para reenviar el correo de confirmación", required = true) @RequestBody ResendEmailRequest request) {
        Optional<Usuario> optionalUser = Optional.ofNullable((Usuario) usuarioRepository.findByEmail(request.email()));
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe un usuario con el correo: " + request.email());
        }

        Usuario user = optionalUser.get();

        String subject = "Reenvío de confirmación de registro";
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
 

                <a href="http://localhost/iniciar-sesion" style="color: #1a73e8;" target="_blank">Iniciar sesión</a>
 

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

        emailService.sendConfirmationEmail(user.getEmail(), subject, text);

        return ResponseEntity.ok("Correo de confirmación reenviado a " + request.email());
    }
}