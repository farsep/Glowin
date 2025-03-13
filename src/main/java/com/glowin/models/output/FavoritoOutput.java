package com.glowin.models.output;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record FavoritoOutput(
        Long id,
        Long usuarioId,
        Long servicioId,
        LocalDate fechaAgregado
) {
    public FavoritoOutput(Long id, Long usuarioId, Long servicioId, LocalDateTime fechaAgregado) {
        this(id, usuarioId, servicioId, fechaAgregado.toLocalDate());
    }
}
