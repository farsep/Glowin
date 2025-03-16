package com.glowin.models.Input;

import jakarta.validation.constraints.NotBlank;

public record FavoritoInput(
        @NotBlank
        Long idUsuario,
        @NotBlank
        Long idServicio
) {
}
