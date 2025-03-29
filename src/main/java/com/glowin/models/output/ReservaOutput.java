package com.glowin.models.output;

import com.glowin.models.Empleado;
import com.glowin.models.Reserva;
import com.glowin.models.Servicio;
import com.glowin.models.Usuario;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservaOutput(Long id, String estado, LocalDate fecha, LocalTime hora, EmpleadoOutput empleado, UsuarioOutput usuario, ServicioOutput servicio, LocalDate fechaCreacion, LocalTime horaCreacion ) {


    public ReservaOutput(Reserva reserva, Empleado empleado, Usuario usuario, Servicio servicio) {
        this(
                reserva.getId(),
                reserva.getEstado().fullName(),
                reserva.getFecha(),
                reserva.getHora(),
                new EmpleadoOutput(empleado),
                new UsuarioOutput(usuario),
                new ServicioOutput(servicio),
                reserva.getFechaCreacion(),
                reserva.getHoraCreacion()
        );
    }
}
