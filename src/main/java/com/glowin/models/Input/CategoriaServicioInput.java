package com.glowin.models.Input;

import jakarta.validation.constraints.NotBlank;


public record CategoriaServicioInput(
        @NotBlank
        String nombre,
        @NotBlank
        String urlImagen
) {
        public String getNombre() {
                return nombre;
        }
}
