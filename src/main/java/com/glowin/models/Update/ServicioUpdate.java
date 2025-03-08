package com.glowin.models.Update;

import com.glowin.models.CategoriaServicio;

import java.math.BigDecimal;

/**
 * DTO for updating {@link com.glowin.models.Servicio}
 */

public record ServicioUpdate(
        String nombre,
        String descripcion,
        Integer duracionMinutos,
        Integer cantidadSesiones,
        BigDecimal costo,
        Long categoriaId,
        CategoriaServicio categoria
) {
}
