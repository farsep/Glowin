package com.glowin.model.output;

import com.glowin.model.Empleado;

public record EmpleadoOutput(Long id, String nombre, String apellido, String tipoJornada) {
    public EmpleadoOutput(Empleado empleado) {
        this(empleado.getId(), empleado.getNombre(), empleado.getApellido(), empleado.getTipoJornada().name());
    }
}

