package com.glowin.controller;

import com.glowin.models.LoginRequest;
import com.glowin.models.Usuario;
import com.glowin.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUsuarioRepository usuarioRepository;

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
}
