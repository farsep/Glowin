package com.glowin.models.Update;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.glowin.models.enums.TipoJornada;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for updating {@link com.glowin.models.Empleado}
 */
public record EmpleadoUpdate(
        String nombre,
        String apellido,
        String email,
        String celular,
        BigDecimal salario,
        String dni,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate fechaRegistro,
        TipoJornada tipoJornada) {
}