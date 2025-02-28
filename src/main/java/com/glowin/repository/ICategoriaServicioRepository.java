package com.glowin.repository;

import com.glowin.models.CategoriaServicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICategoriaServicioRepository extends JpaRepository<CategoriaServicio, Long> {
    List<CategoriaServicio> findAll();
}
