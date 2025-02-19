package com.glowin.model.output;

import com.glowin.model.Usuario;
import com.glowin.model.enums.Rol;

public record UsuarioOutput(String nombre, String apellido, Rol rol) {
    public UsuarioOutput(Usuario usuario) {
        this(usuario.getNombre(), usuario.getApellido(), usuario.getRol());
    }
}
