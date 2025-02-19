package com.glowin.model.output;

import com.glowin.model.Servicio;

import java.math.BigDecimal;

public record ServicioOutput(Long id, String nombre, String descripcion, Integer duracion, BigDecimal costo) {
    public ServicioOutput(Servicio servicio) {
        this(servicio.getId(), servicio.getNombre(), servicio.getDescripcion(), servicio.getDuracion(), servicio.getCosto());
    }
}
