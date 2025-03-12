package com.glowin.models;

import com.glowin.models.Input.ImagenServicioInput;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "imagenes_servicios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImagenServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    private String descripcion;

    @Column(name = "url_imagen", nullable = false)
    private String urlImagen;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fechaCreacion;

    // Relación Muchos a Uno con Servicio
    @ManyToOne
    @JoinColumn(name = "id_servicio", nullable = false)
    private Servicio servicio;

    public ImagenServicio(ImagenServicioInput imagenServicioInput, Servicio servicio) {
        this.titulo = imagenServicioInput.titulo();
        this.descripcion =imagenServicioInput.descripcion();
        this.urlImagen = imagenServicioInput.urlImagen();
        this.fechaCreacion = imagenServicioInput.fechaCreacion() != null ? imagenServicioInput.fechaCreacion(): LocalDate.now();
        this.servicio = servicio;
    }
}
