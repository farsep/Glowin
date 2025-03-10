package com.glowin.service;

import com.glowin.models.Usuario;
import com.glowin.models.Input.UsuarioInput;
import com.glowin.models.enums.Rol;
import com.glowin.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Metodo para registrar un usuario
    public Usuario registrarUsuario(UsuarioInput usuarioInput) {
        // Verificar si el email ya está en uso
        if (usuarioRepository.existsByEmail(usuarioInput.email())) {
            throw new RuntimeException("El email ya está en uso");
        }

        // Verificar si el rol es SUPER_ADMINISTRADOR y ya existe uno
        if (Rol.fromString(usuarioInput.rol()) == Rol.SUPER_ADMINISTRADOR && usuarioRepository.existsByRol(Rol.SUPER_ADMINISTRADOR)) {
            throw new RuntimeException("Ya existe un usuario con el rol SUPER_ADMINISTRADOR");
        }

        // Crear el usuario y encriptar la contraseña
        Usuario usuario = new Usuario(usuarioInput);
        usuario.setPassword(passwordEncoder.encode(usuarioInput.password())); // Encriptar la contraseña
        return usuarioRepository.save(usuario);
    }

    // Metodo para autenticar un usuario
    public boolean autenticarUsuario(String email, String password) {
        UserDetails userDetails = usuarioRepository.findByEmail(email); // Devuelve UserDetails

        // Verificar si el usuario existe
        if (userDetails == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        // Verificar la contraseña
        return passwordEncoder.matches(password, userDetails.getPassword());
    }
}