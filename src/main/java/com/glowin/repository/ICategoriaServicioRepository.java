package com.glowin.repository;

import com.glowin.models.CategoriaServicio;
import com.glowin.models.Servicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ICategoriaServicioRepository extends JpaRepository<CategoriaServicio, Long> {
    List<CategoriaServicio> findAll();
    Optional<CategoriaServicio> findByNombre(String nombre);

    Page<Servicio> findByNombreContaining(String query, Pageable pageable);
}
