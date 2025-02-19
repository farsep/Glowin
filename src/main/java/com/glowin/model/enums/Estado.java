package com.glowin.model.enums;

public enum Estado {
    PENDIENTE,
    CONFIRMADA,
    CANCELADA;

    public static Estado fromString(String estado) {
        //now created a constructor that doesn't distingish between upper and lower case or even spaces
        return Estado.valueOf(estado.toUpperCase().trim().replace(" ", ""));
    }
}
