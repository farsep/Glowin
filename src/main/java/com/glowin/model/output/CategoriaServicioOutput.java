package com.glowin.model.output;

import com.glowin.model.CategoriaServicio;

public record CategoriaServicioOutput(String nombre) {

    public CategoriaServicioOutput(CategoriaServicio categoriaServicio) {
        this(categoriaServicio.getNombre());
    }
}
