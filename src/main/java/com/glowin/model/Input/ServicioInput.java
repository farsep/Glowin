package com.glowin.model.Input;

import java.math.BigDecimal;

public record ServicioInput(
        String nombre,
        String descripcion,
        Integer duracion,
        BigDecimal costo,
        Long categoriaId
) {
}
