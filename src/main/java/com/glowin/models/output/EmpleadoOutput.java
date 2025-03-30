package com.glowin.models.output;

import com.glowin.models.Empleado;

public record EmpleadoOutput(Long id, String nombre, String apellido, String tipoJornada, String profesion, String urlFoto) {
    public EmpleadoOutput(Empleado empleado) {
        this(empleado.getId(), empleado.getNombre(), empleado.getApellido(), empleado.getTipoJornada().name(), empleado.getProfesion(), empleado.getUrlFoto());
    }
}