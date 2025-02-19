package com.glowin.model.Input;

public record ReservaInput(
        Long idCliente,
        Long idServicio,
        Long idEmpleado,
        String fecha,
        String hora,
        String estado
) {
}
