package com.glowin.models.Input;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record FavoritoInput(
        @NotNull
        Long idUsuario,
        @NotNull
        Long idServicio,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate fechaAgregado
) {
}
