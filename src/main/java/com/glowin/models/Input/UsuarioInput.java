package com.glowin.models.Input;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.glowin.models.enums.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for {@link com.glowin.models.Usuario}
 */
public record UsuarioInput(
        @NotBlank
        String nombre,
        @NotBlank
        String apellido,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String celular,
        @NotBlank
        String password,
        @NotBlank
        String rol
){
}
// JSON no tiene un tipo LocalDate o LocalTime, las fechas suelen enviarse como String en formato "yyyy-MM-dd" y "HH:mm:ss"