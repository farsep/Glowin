package com.glowin.model;

import com.glowin.model.Input.ReservaInput;
import com.glowin.model.enums.Estado;
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
    @NonNull
    private Long idCliente;
    @NonNull
    private Long idServicio;
    @NonNull
    private Long idEmpleado;
    @NonNull
    private LocalDate fecha;
    @NonNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalTime hora;
    @NonNull
    private Estado estado;

    public Reserva(Long idCliente, Long idServicio, Long idEmpleado, LocalDate fecha, LocalTime hora, Estado estado) {
        this.idCliente = idCliente;
        this.idServicio = idServicio;
        this.idEmpleado = idEmpleado;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
    }

    public Reserva(ReservaInput reserva) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.idCliente = reserva.idCliente();
        this.idServicio = reserva.idServicio();
        this.idEmpleado = reserva.idEmpleado();
        this.fecha = LocalDate.parse(reserva.fecha(), dateFormatter);
        this.hora = LocalTime.parse(reserva.hora(), timeFormatter);
        this.estado = Estado.fromString(reserva.estado());
    }
}
