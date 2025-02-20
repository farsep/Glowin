package com.glowin.models.Input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReservaInput(
        @NotNull
        Long idCliente,
        @NotNull
        Long idServicio,
        @NotNull
        Long idEmpleado,
        @NotBlank
        String fecha,
        @NotBlank
        String hora,
        @NotBlank
        String estado
) {
}
