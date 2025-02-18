package com.glowin.models;

import com.glowin.models.Input.ServicioInput;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Set;

@Entity
@Table(name = "servicios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NonNull
    private String nombre;
    @NonNull
    private String descripcion;
    @NonNull
    private Integer duracion;
    @NonNull
    private BigDecimal costo;
    @ManyToOne
    private CategoriaServicio categoria;

    @ManyToMany
    @JoinTable(
            name = "servicio_empleado",
            joinColumns = @JoinColumn(name = "id_servicio"),
            inverseJoinColumns = @JoinColumn(name = "id_empleado")
    )
    private Set<Empleado> empleados;

    public Servicio(String nombre, String descripcion, Integer duracion, BigDecimal costo, CategoriaServicio categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracion = duracion;
        this.costo = costo;
        this.categoria = categoria;
    }

    public Servicio(ServicioInput servicioInput) {
        this.nombre = servicioInput.nombre();
        this.descripcion = servicioInput.descripcion();
        this.duracion = servicioInput.duracion();
        this.costo = servicioInput.costo();
    }
}