package com.glowin.models.output;

import com.glowin.models.Servicio;

import java.math.BigDecimal;

public record ServicioOutput(Long id, String nombre, String descripcion, Integer duracionMinutos, BigDecimal costo, Integer cantidadSesiones, String categoria) {
    public ServicioOutput(Servicio servicio) {
        this(servicio.getId(), servicio.getNombre(), servicio.getDescripcion(), servicio.getDuracionMinutos(), servicio.getCosto(), servicio.getCantidadSesiones(), servicio.getCategoria().getNombre());
    }
}