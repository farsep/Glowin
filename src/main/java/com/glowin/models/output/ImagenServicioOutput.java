package com.glowin.models.output;

import com.glowin.models.ImagenServicio;

import java.time.LocalDate;

public record ImagenServicioOutput(Long id,
                                   String titulo,
                                   String descripcion,
                                   String urlImagen,
                                   LocalDate fechaCreacion,
                                   Long idServicio,
                                   String nombreServicio) {
    public ImagenServicioOutput(ImagenServicio img) {
        this(
                img.getId(),
                img.getTitulo(),
                img.getDescripcion(),
                img.getUrlImagen(),
                img.getFechaCreacion(),
                img.getServicio().getId(),
                img.getServicio().getNombre() // Nombre del servicio
        );
    }
}
