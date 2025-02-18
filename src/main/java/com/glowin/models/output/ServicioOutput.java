package com.glowin.models.output;

import com.glowin.models.Servicio;

import java.math.BigDecimal;

public record ServicioOutput(String nombre, String descripcion, Integer duracion, BigDecimal costo) {
    public ServicioOutput(Servicio servicio) {
        this(servicio.getNombre(), servicio.getDescripcion(), servicio.getDuracion(), servicio.getCosto());
    }
}
