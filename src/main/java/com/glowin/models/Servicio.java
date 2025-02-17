package com.glowin.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Duration;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Servicio {
    private Long id;
    private String nombre;
    private String descripcion;
    private Duration duracion;
    private BigDecimal costo;
    private CategoriaServicio categoria;
}
