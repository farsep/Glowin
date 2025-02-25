package com.glowin.models.Update;

import com.glowin.models.enums.Rol;

/**
 * DTO for updating {@link com.glowin.models.Usuario}
 */
public record UsuarioUpdate(
        String nombre,
        String apellido,
        String email,
        String password,
        Rol rol) {
}
// Los campos son opcionales porque en una actualización no siempre se requiere modificar todos.