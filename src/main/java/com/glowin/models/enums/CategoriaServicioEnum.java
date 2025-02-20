package com.glowin.models.enums;

public enum CategoriaServicioEnum {
    CABELLO,
    UNIAS,
    PESTANIAS,
    FACIAL_MAQUILLAJE,
    CEJAS,
    CORPORAL_DEPILACION,
    GLOWIN_MEN;

    public static CategoriaServicioEnum fromString(String categoria) {
        for (CategoriaServicioEnum categoriaServicioEnum : CategoriaServicioEnum.values()) {
            if (categoriaServicioEnum.name().trim().replace(" ","").equalsIgnoreCase(categoria)) {
                return categoriaServicioEnum;
            }
        }
        return null;
    }
}
