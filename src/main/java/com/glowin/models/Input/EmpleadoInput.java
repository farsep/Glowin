package com.glowin.models.Input;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

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
        @NotBlank
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate fechaRegistro,
        @NotBlank
        String tipoJornada
) {
}
