package com.glowin.models.Update;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.glowin.models.enums.Rol;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for updating {@link com.glowin.models.Usuario}
 */
public record UsuarioUpdate(
        String nombre,
        String apellido,
        String email,
        String password,
        String celular,
        Rol rol,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate fechaRegistro,
        @JsonFormat(pattern = "HH:mm:ss")
        LocalTime horaRegistro) {
}
// Los campos son opcionales porque en una actualización no siempre se requiere modificar todos.