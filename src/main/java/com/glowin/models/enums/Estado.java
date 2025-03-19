package com.glowin.models.enums;

public enum Estado {
    CONCLUIDA("CONCLUIDA"),
    EN_CURSO("EN CURSO"),
    CONFIRMADA("CONFIRMADA"),
    CANCELADA("CANCELADA");

    private final String fullName;

    Estado(String s) {
        fullName = s;
    }

    public static Estado fromString(String estado) {
        //now created a constructor that doesn't distingish between upper and lower case or even spaces
        return Estado.valueOf(estado.toUpperCase().trim().replace(" ", "_"));
    }

    public String fullName() {
        return fullName;
    }
}
