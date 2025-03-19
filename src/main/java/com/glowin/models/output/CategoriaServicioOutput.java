package com.glowin.models.output;

import com.glowin.models.CategoriaServicio;

public record CategoriaServicioOutput(Long id, String nombre, String urlImagen, String enlaceFacebook, String enlaceWhatsApp) {
    public CategoriaServicioOutput(CategoriaServicio categoriaServicio, String enlaceFacebook, String enlaceWhatsApp) {
        this(categoriaServicio.getId(), categoriaServicio.getNombre(), categoriaServicio.getUrlImagen(), enlaceFacebook, enlaceWhatsApp);
    }
    public CategoriaServicioOutput(CategoriaServicio categoriaServicio) {
        this(categoriaServicio.getId(), categoriaServicio.getNombre(), categoriaServicio.getUrlImagen(), null, null);
    }
}