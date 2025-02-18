package com.glowin.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServicioEmpleado {
    private Long id;
    private Long idServicio;
    private Long idEmpleado;
}
