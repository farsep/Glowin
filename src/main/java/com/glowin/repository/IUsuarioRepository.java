package com.glowin.repository;

import com.glowin.models.Usuario;
import com.glowin.models.enums.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findById(Long id);

    // sirve para verificar si el rol SUPER_ADMINISTRADOR ya existe en la base de datos
    boolean existsByRol(Rol rol);
    boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email);
}