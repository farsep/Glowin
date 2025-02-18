package com.glowin.models.output;

import com.glowin.models.Empleado;

public record EmpleadoOutput(String nombre, String apellido, String tipoJornada) {
    public EmpleadoOutput(Empleado empleado) {
        this(empleado.getNombre(), empleado.getApellido(), empleado.getTipoJornada().name());
    }
}

