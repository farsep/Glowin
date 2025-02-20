package com.glowin.models.output;

import com.glowin.models.CategoriaServicio;

public record CategoriaServicioOutput(String nombre) {


    public CategoriaServicioOutput(CategoriaServicio categoriaServicio1) {
        this(categoriaServicio1.getNombre().toString());
    }
}
