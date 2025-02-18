package com.glowin.models.output;

import com.glowin.models.CategoriaServicio;

public record CategoriaServicioOutput(String nombre) {

    public CategoriaServicioOutput(CategoriaServicio categoriaServicio) {
        this(categoriaServicio.getNombre());
    }
}
