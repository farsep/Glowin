package com.glowin.model.enums;

public enum TipoJornada {
    MATUTINA,
    VESPERTINA;

    public static TipoJornada fromString(String rol) {
        //now created a constructor that doesn't distingish between upper and lower case or even spaces
        return TipoJornada.valueOf(rol.toUpperCase().trim().replace(" ", ""));
    }
}
