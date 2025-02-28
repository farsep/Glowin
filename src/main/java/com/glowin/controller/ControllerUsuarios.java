package com.glowin.controller;

import com.glowin.models.Input.UsuarioInput;
import com.glowin.models.Update.UsuarioUpdate;
import com.glowin.models.Usuario;
import com.glowin.models.enums.Rol;
import com.glowin.models.output.UsuarioOutput;
import com.glowin.repository.IUsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @GetMapping("/{id}")
    public ResponseEntity<UsuarioOutput> getUser(@PathVariable Long id) {
        Optional<Usuario> user = usuarioRepository.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(new UsuarioOutput(user.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<UsuarioOutput>> getAllUsers() {
        List<Usuario> users = usuarioRepository.findAll();
        List<UsuarioOutput> userOutputs = users.stream().map(UsuarioOutput::new).toList();
        return ResponseEntity.ok(userOutputs);
    }


    @Transactional
    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody UsuarioInput usuarioInput) {

        // valida si el email ya está en uso
        if (usuarioRepository.existsByEmail(usuarioInput.email())) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "El email ya está en uso");
            response.put("status", "409");
            response.put("timestamp", LocalDate.now().toString());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        // valida si el rol es SUPER_ADMINISTRADOR y si ya existe un usuario con ese rol
        // si el rol es SUPER_ADMINISTRADOR y ya existe un usuario con ese rol, retorna un error 409
        Rol nuevoRol = Rol.fromString(usuarioInput.rol());
        if (nuevoRol == Rol.SUPER_ADMINISTRADOR && usuarioRepository.existsByRol(Rol.SUPER_ADMINISTRADOR)) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Ya existe un usuario con el rol SUPER_ADMINISTRADOR");
            response.put("status", "409");
            response.put("timestamp", LocalDate.now().toString());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        Usuario user = new Usuario(usuarioInput);
        usuarioRepository.save(user);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                        .buildAndExpand(user.getId()).toUri()).body(new UsuarioOutput(user));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UsuarioUpdate usuarioUpdate) {
        Optional<Usuario> optionalUser = usuarioRepository.findById(id);

        if (optionalUser.isPresent()) {
            Usuario user = optionalUser.get();

            // Validar que el nuevo email no esté en uso por otro usuario
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

            // Validar si el nuevo rol es SUPER_ADMINISTRADOR y ya existe uno en la BD
            if (usuarioUpdate.rol() != null && usuarioUpdate.rol() == Rol.SUPER_ADMINISTRADOR &&
                    usuarioRepository.existsByRol(Rol.SUPER_ADMINISTRADOR)) {
                Map<String, String> response = new HashMap<>();
                response.put("error", "Ya existe un usuario con el rol SUPER_ADMINISTRADOR");
                response.put("status", "409");
                response.put("timestamp", LocalDate.now().toString());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            // Actualizar solo los valores no nulos
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id, @RequestParam(required = false) Long nuevoSuperAdminId) {
        Optional<Usuario> user = usuarioRepository.findById(id);

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuario = user.get();

        // Si el usuario es SUPER_ADMINISTRADOR, debe transferir el rol antes de eliminarse
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

            // Eliminar al usuario original primero
            usuarioRepository.delete(usuario);

            // Actualizar roles
            nuevoSuperAdmin.setRol(Rol.SUPER_ADMINISTRADOR);
            usuarioRepository.save(nuevoSuperAdmin);
        } else {
            // Eliminar al usuario original si no es SUPER_ADMINISTRADOR
            usuarioRepository.delete(usuario);
        }

        return ResponseEntity.ok(Map.of(
                "message", "Usuario eliminado con éxito",
                "status", "200",
                "timestamp", LocalDate.now().toString()
        ));
    }

}
