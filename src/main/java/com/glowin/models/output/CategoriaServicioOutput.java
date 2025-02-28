package com.glowin.models.output;

import com.glowin.models.CategoriaServicio;

public record CategoriaServicioOutput(Long id, String nombre) {
    public CategoriaServicioOutput(CategoriaServicio categoriaServicio) {
        this(categoriaServicio.getId(), categoriaServicio.getNombre());
    }
}
