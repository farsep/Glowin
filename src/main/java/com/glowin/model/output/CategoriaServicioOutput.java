package com.glowin.model.output;

import com.glowin.model.CategoriaServicio;

public record CategoriaServicioOutput(Long id, String nombre) {

    public CategoriaServicioOutput(CategoriaServicio categoriaServicio) {
        this(categoriaServicio.getId(),categoriaServicio.getNombre());
    }
}
