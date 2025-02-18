package com.glowin.repository;

import com.glowin.models.CategoriaServicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoriaServicioRepository extends JpaRepository<CategoriaServicio, Long> {
    Page<CategoriaServicio> findAll(Pageable pageable);
}
