package com.glowin.models.output;

import com.glowin.models.Usuario;
import com.glowin.models.enums.Rol;

public record UsuarioOutput(Long id, String nombre, String apellido, Rol rol, String email, String celular) {
    public UsuarioOutput(Usuario usuario) {
        this(usuario.getId(), usuario.getNombre(), usuario.getApellido(), usuario.getRol(), usuario.getEmail(), usuario.getCelular());
    }
}