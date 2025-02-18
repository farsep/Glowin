package com.glowin.repository;

import com.glowin.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByID(Long id);
    boolean existsByEmail(String email);
}
