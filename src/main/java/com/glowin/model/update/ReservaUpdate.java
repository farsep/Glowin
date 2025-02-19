package com.glowin.model.update;

import com.glowin.model.enums.Estado;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ReservaUpdate {
    private Long idCliente;
    private Long idServicio;
    private Long idEmpleado;
    private LocalDate fecha;
    private LocalTime hora;
    private Estado estado;

}
