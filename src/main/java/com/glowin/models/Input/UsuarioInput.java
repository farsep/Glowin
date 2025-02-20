package com.glowin.models.Input;

import com.glowin.models.enums.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;

/**
 * DTO for {@link com.glowin.models.Usuario}
 */
public record UsuarioInput(
        @NotBlank
        String nombre,
        @NotBlank
        String apellido,
        @Email
        String email,
        @NotBlank
        String password,
        @NotBlank
        String rol){
}