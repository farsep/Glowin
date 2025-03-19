package com.glowin.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.glowin.models.Input.ServicioInput;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "servicios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String nombre;
    private String descripcion;
    private Integer duracionMinutos;
    private BigDecimal costo;
    private Integer cantidadSesiones;

    // Nuevo campo para mapear la columna "nombre_categoria"
    @Column(name = "nombre_categoria", nullable = false)
    private String nombreCategoria;



    @JsonBackReference // Evita la serialización cíclica con `CategoriaServicio`
    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private CategoriaServicio categoria;

    @OneToMany(mappedBy = "servicio")
    @JsonIgnore
    private Set<ImagenServicio> imagenes;

    @JsonIgnore // Evita loops en la serialización
    @OneToMany(mappedBy = "servicio")
    private Set<Reserva> reservas;

    @JsonIgnore // Evita loops con empleados
    @ManyToMany
    @JoinTable(
            name = "empleados_servicios",
            joinColumns = @JoinColumn(name = "id_servicio"),
            inverseJoinColumns = @JoinColumn(name = "id_empleado")
    )
    private Set<Empleado> empleados = new HashSet<>(); // Evitar NullPointerException;

    public Servicio(ServicioInput servicioInput, CategoriaServicio categoria) {
        this.nombre = servicioInput.nombre();
        this.descripcion = servicioInput.descripcion();
        this.duracionMinutos = servicioInput.duracionMinutos();
        this.costo = servicioInput.costo();
        this.cantidadSesiones = servicioInput.cantidadSesiones();
        this.categoria = categoria;
        // Asignar el nombre de la categoría para cumplir con la restricción NOT NULL
        this.nombreCategoria = categoria.getNombre();
    }
}