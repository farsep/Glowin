package com.glowin.model.Input;

import jakarta.validation.constraints.NotBlank;

public record CategoriaServicioInput(
        @NotBlank
        String nombre
) {
}
