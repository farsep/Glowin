package com.glowin.repository;

import com.glowin.models.ImagenServicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IImagenServicioRepository extends JpaRepository<ImagenServicio, Long> {
    // Opcional: métodos para buscar por servicio, ej:
    List<ImagenServicio> findByServicioId(Long idServicio);
}
