package com.glowin.service;

import com.glowin.dto.CategoriaServicioDetallesDTO;
import com.glowin.models.CategoriaServicio;
import com.glowin.repository.ICategoriaServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CategoriaServicioService {

    @Autowired
    private ICategoriaServicioRepository categoriaServicioRepository;

    public CategoriaServicioDetallesDTO obtenerDetallesCategoriaServicio(Long id) {
        CategoriaServicio categoriaServicio = categoriaServicioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Categoría de servicio no encontrada"));
        String enlace = "https://tudominio.com/categorias-servicios/" + id;
        return new CategoriaServicioDetallesDTO(categoriaServicio.getNombre(), categoriaServicio.getUrlImagen(), enlace);
    }
}