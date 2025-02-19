package com.glowin.model.output;

import com.glowin.model.Empleado;
import com.glowin.model.Reserva;
import com.glowin.model.enums.Estado;
import com.glowin.model.Servicio;
import com.glowin.model.Usuario;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservaOutput(Long id, Estado estado, LocalDate fecha, LocalTime hora, EmpleadoOutput empleado, UsuarioOutput usuario, ServicioOutput servicio ) {


    public ReservaOutput(Reserva reserva, Empleado empleado, Usuario usuario, Servicio servicio) {
        this(
                reserva.getID(),
                reserva.getEstado(),
                reserva.getFecha(),
                reserva.getHora(),
                new EmpleadoOutput(empleado),
                new UsuarioOutput(usuario),
                new ServicioOutput(servicio)
        );
    }
}
