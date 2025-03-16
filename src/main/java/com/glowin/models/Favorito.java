package com.glowin.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Table(name = "favoritos", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_usuario", "id_servicio"}) // Un usuario no puede marcar dos veces el mismo servicio
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Favorito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_servicio", nullable = false)
    private Servicio servicio;

    @Column(name = "fecha_agregado", nullable = false)
    private LocalDate fechaAgregado;

    public Favorito(Usuario usuario, Servicio servicio) {
        this.usuario = usuario;
        this.servicio = servicio;
        this.fechaAgregado = LocalDate.now();
    }
}
