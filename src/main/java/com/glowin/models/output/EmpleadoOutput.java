package com.glowin.models.output;

import com.glowin.models.Empleado;

public record EmpleadoOutput(Long id, String nombre, String apellido, String tipoJornada) {
    public EmpleadoOutput(Empleado empleado) {
        this(empleado.getId(), empleado.getNombre(), empleado.getApellido(), empleado.getTipoJornada().name());
    }
}