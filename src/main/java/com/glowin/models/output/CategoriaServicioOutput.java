package com.glowin.models.output;

import com.glowin.models.CategoriaServicio;

public record CategoriaServicioOutput(Long id, String nombre, String urlImagen) {
    public CategoriaServicioOutput(CategoriaServicio categoriaServicio) {
        this(categoriaServicio.getId(), categoriaServicio.getNombre(), categoriaServicio.getUrlImagen());
    }
}
