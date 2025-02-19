package com.glowin.model.Input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record EmpleadoInput(
        @NotBlank
        String nombre,
        @NotBlank
        String apellido,
        @Email
        String email,
        @NotBlank
        String celular,
        @NotNull
        BigDecimal salario,
        @NotBlank
        String dni,
        String tipoJornada
) {
}
