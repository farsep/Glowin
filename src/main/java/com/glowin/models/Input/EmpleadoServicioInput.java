package com.glowin.models.Input;

import jakarta.validation.constraints.NotNull;

public record EmpleadoServicioInput(
        @NotNull
        Long idEmpleado,
        @NotNull
        Long idServicio
) {
}
