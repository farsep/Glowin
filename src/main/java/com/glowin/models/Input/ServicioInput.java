package com.glowin.models.Input;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ServicioInput(
        @NotBlank
        String nombre,

        @NotBlank
        String descripcion,

        @Positive
        @NotNull
        Integer duracionMinutos,

        @Digits(integer = 30, fraction = 2)
        @Positive
        BigDecimal costo,

        @Positive
        @NotNull
        Integer cantidadSesiones,

        Long categoriaId
) {
}
