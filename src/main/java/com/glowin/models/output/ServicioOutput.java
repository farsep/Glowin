package com.glowin.models.output;

import com.glowin.models.Servicio;

import java.math.BigDecimal;

public record ServicioOutput(Long id, String nombre, String descripcion, Integer duracionMinutos, BigDecimal costo, Integer cantidadSesiones, String categoria, Long categoriaId, String enlaceFacebook, String enlaceWhatsApp) {
    public ServicioOutput(Servicio servicio, String enlaceFacebook, String enlaceWhatsApp) {
        this(servicio.getId(), servicio.getNombre(), servicio.getDescripcion(), servicio.getDuracionMinutos(), servicio.getCosto(), servicio.getCantidadSesiones(), servicio.getCategoria().getNombre(),
                servicio.getCategoria().getId(),  enlaceFacebook, enlaceWhatsApp);
    }

    public ServicioOutput(Servicio servicio) {
        this(servicio.getId(), servicio.getNombre(), servicio.getDescripcion(), servicio.getDuracionMinutos(), servicio.getCosto(), servicio.getCantidadSesiones(), servicio.getCategoria().getNombre(),
                servicio.getCategoria().getId(), null, null);
    }
}