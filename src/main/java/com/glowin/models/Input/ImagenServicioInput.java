package com.glowin.models.Input;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public record ImagenServicioInput(
        @NotBlank
        String titulo,
        String descripcion,
        @NotBlank
        String urlImagen,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate fechaCreacion,
        Long idServicio, // Opción 1: Enviar solo el ID del servicio
        ServicioInput servicio // Opción 2: Enviar un servicio completo
) {
}
