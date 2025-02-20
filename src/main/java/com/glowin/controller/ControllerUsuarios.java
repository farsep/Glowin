package com.glowin.controller;

import com.glowin.models.Input.UsuarioInput;
import com.glowin.models.Usuario;
import com.glowin.models.output.UsuarioOutput;
import com.glowin.repository.IUsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Transactional
    @PostMapping
    public ResponseEntity<UsuarioOutput> registerUser(@RequestBody UsuarioInput usuarioInput) {
        Usuario user = new Usuario(usuarioInput);
        usuarioRepository.save(user);
        return ResponseEntity.ok(new UsuarioOutput(user));
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<UsuarioOutput> updateUser(@PathVariable Long id, @RequestBody UsuarioInput usuarioInput) {
//        Optional<Usuario> user = usuarioRepository.findById(id);
//        if (user.isPresent()) {
//            Usuario user1 = user.get();
//            user1.update(usuarioInput);
//            usuarioRepository.save(user1);
//            return ResponseEntity.ok(new UsuarioOutput(user1));
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UsuarioOutput> deleteUser(@PathVariable Long id) {
        Optional<Usuario> user = usuarioRepository.findById(id);
        if (user.isPresent()) {
            usuarioRepository.delete(user.get());
            return ResponseEntity.ok(new UsuarioOutput(user.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
