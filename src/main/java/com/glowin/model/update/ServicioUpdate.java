package com.glowin.model.update;


import com.glowin.model.CategoriaServicio;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServicioUpdate {
    private String nombre;
    private String descripcion;
    private Integer duracion;
    private BigDecimal costo;
    private CategoriaServicio categoria;
}
