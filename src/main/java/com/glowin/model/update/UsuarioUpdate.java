package com.glowin.model.update;

import lombok.Data;

@Data
public class UsuarioUpdate {
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String rol;
}