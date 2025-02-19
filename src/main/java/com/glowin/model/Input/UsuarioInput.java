package com.glowin.model.Input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

/**
 * DTO for {@link com.glowin.model.Usuario}
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
        @NotEmpty
        String rol){
}