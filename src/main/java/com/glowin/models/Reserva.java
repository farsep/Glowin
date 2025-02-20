package com.glowin.models;

import com.glowin.converter.EstadoConverter;
import com.glowin.converter.RolConverter;
import com.glowin.models.Input.ReservaInput;
import com.glowin.models.enums.Estado;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "reservas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Usuario cliente;

    @ManyToOne
    @JoinColumn(name = "id_servicio", nullable = false)
    private Servicio servicio;

    @ManyToOne
    @JoinColumn(name = "id_empleado", nullable = false)
    private Empleado empleado;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalTime hora;

    @Convert(converter = EstadoConverter.class)
    @Column(name = "estado", nullable = false)
    @ColumnDefault("PENDIENTE")
    private Estado estado;

    public Reserva(Usuario cliente, Servicio servicio, Empleado empleado, LocalDate fecha, LocalTime hora, Estado estado) {
        this.cliente = cliente;
        this.servicio = servicio;
        this.empleado = empleado;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
    }

    public Reserva(ReservaInput reserva, Usuario cliente, Servicio servicio, Empleado empleado) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.cliente = cliente;
        this.servicio = servicio;
        this.empleado = empleado;
        this.fecha = LocalDate.parse(reserva.fecha(), dateFormatter);
        this.hora = LocalTime.parse(reserva.hora(), timeFormatter);
        this.estado = Estado.fromString(reserva.estado());
    }
}