package com.glowin.models.enums;

public enum Rol {
    SUPER_ADMINISTRADOR,
    ADMINISTRADOR,
    CLIENTE;

    public static Rol fromString(String rol) {
        //now created a constructor that doesn't distingish between upper and lower case or even spaces
        return Rol.valueOf(rol.toUpperCase().trim().replace(" ", ""));
    }

}
