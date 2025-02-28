package com.glowin.models.Input;

import jakarta.validation.constraints.NotBlank;

public record CategoriaServicioInput(
        @NotBlank
        String nombre
) {
        public String getNombre() {
                return nombre;
        }
}
