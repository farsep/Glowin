package com.glowin.models.Input;

public record ReservaInput(
        Long idCliente,
        Long idServicio,
        Long idEmpleado,
        String fecha,
        String hora,
        String estado
) {
}
