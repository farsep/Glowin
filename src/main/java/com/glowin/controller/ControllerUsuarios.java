package com.glowin.controller;

import com.glowin.models.Input.UsuarioInput;
import com.glowin.models.Update.UsuarioUpdate;
import com.glowin.models.Usuario;
import com.glowin.models.enums.Rol;
import com.glowin.models.output.UsuarioOutput;
import com.glowin.repository.IUsuarioRepository;
import com.google.gson.JsonObject;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
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

    @GetMapping
    public ResponseEntity<List<UsuarioOutput>> getAllUsers() {
        List<Usuario> users = usuarioRepository.findAll();
        List<UsuarioOutput> userOutputs = users.stream().map(UsuarioOutput::new).toList();
        return ResponseEntity.ok(userOutputs);
    }



    @Transactional
    @PostMapping
    public ResponseEntity<UsuarioOutput> registerUser(@RequestBody UsuarioInput usuarioInput) {
        Usuario user = new Usuario(usuarioInput);
        usuarioRepository.save(user);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                        .buildAndExpand(user.getId()).toUri()).body(new UsuarioOutput(user));
    }


    @PutMapping("/{id}")
    public ResponseEntity<UsuarioOutput> updateUser(@PathVariable Long id, @RequestBody UsuarioUpdate usuarioUpdate) {
        Optional<Usuario> optionalUser = usuarioRepository.findById(id);

        if (optionalUser.isPresent()) {
            Usuario user = optionalUser.get();

            // Solo actualiza los campos si no son nulos
            if (usuarioUpdate.nombre() != null) {
                user.setNombre(usuarioUpdate.nombre());
            }
            if (usuarioUpdate.apellido() != null) {
                user.setApellido(usuarioUpdate.apellido());
            }
            if (usuarioUpdate.email() != null) {
                user.setEmail(usuarioUpdate.email());
            }
            if (usuarioUpdate.password() != null) {
                user.setPassword(usuarioUpdate.password());
            }
            if (usuarioUpdate.rol() != null) {
                user.setRol(Rol.fromString(String.valueOf(usuarioUpdate.rol())));
            }

            usuarioRepository.save(user);
            return ResponseEntity.ok(new UsuarioOutput(user));
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        Optional<Usuario> user = usuarioRepository.findById(id);
        if (user.isPresent()) {
            usuarioRepository.delete(user.get());
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("message", "User deleted successfully");
            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", "application/json");
            return ResponseEntity.ok().headers(header).body(jsonObject.toString());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
