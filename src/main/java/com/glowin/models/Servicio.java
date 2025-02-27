package com.glowin.models;

import com.glowin.models.Input.ServicioInput;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private CategoriaServicio categoria;

    @OneToMany(mappedBy = "servicio")
    private Set<Reserva> reservas;

    @ManyToMany
    @JoinTable(
            name = "servicio_empleado",
            joinColumns = @JoinColumn(name = "id_servicio"),
            inverseJoinColumns = @JoinColumn(name = "id_empleado")
    )
    private Set<Empleado> empleados;

    public Servicio(ServicioInput servicioInput, CategoriaServicio categoria) {
        this.nombre = servicioInput.nombre();
        this.descripcion = servicioInput.descripcion();
        this.duracionMinutos = servicioInput.duracionMinutos();
        this.costo = servicioInput.costo();
        this.cantidadSesiones = servicioInput.cantidadSesiones();
        this.categoria = categoria;
    }
}