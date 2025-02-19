package com.glowin.model.output;

import com.glowin.model.Empleado;

public record EmpleadoOutput(String nombre, String apellido, String tipoJornada) {
    public EmpleadoOutput(Empleado empleado) {
        this(empleado.getNombre(), empleado.getApellido(), empleado.getTipoJornada().name());
    }
}

