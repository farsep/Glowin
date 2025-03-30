package com.glowin.models.Update;

public record ReservaUpdate(
        Long idCliente,
        Long idServicio,
        Long idEmpleado,
        String fecha,
        String hora,
        String estado
) {
}
