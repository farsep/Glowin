package com.glowin.models.Update;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record ImagenServicioUpdate(
        String titulo,
        String descripcion,
        String urlImagen,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate fechaCreacion,
        Long idServicio // si quieres permitir cambiar de servicio
) {
    // Todos los campos son opcionales para permitir actualizaciones parciales
}
