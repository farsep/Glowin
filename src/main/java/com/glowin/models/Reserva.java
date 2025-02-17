package com.glowin.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    private Long id;
    private Long idCliente;
    private Long idServicio;
    private Long idEmpleado;
    private LocalDate fecha;
    private LocalTime hora;
    private Estado estado;
}
