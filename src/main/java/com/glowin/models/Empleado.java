package com.glowin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.glowin.models.Input.EmpleadoInput;
import com.glowin.models.enums.TipoJornada;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "empleados")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String nombre;
    private String apellido;
    private String email;
    private String celular;
    private BigDecimal salario;
    private String dni;
    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    @Enumerated
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "tipo_jornada", nullable = false)
    private TipoJornada tipoJornada;

    @OneToMany(mappedBy = "empleado")
    private Set<Reserva> reservas;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "empleados_servicios",
            joinColumns = @JoinColumn(name = "id_empleado"),
            inverseJoinColumns = @JoinColumn(name = "id_servicio")
    )
    private Set<Servicio> servicios = new HashSet<>(); // Evitar NullPointerException;

    public Empleado(String nombre, String apellido, String email, String celular, BigDecimal salario, String dni, LocalDate fechaRegistro,  TipoJornada tipoJornada) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.celular = celular;
        this.salario = salario;
        this.fechaRegistro= fechaRegistro;
        this.dni = dni;
        this.tipoJornada = tipoJornada;
    }

    public Empleado(EmpleadoInput empleadoInput) {
        this.nombre = empleadoInput.nombre();
        this.apellido = empleadoInput.apellido();
        this.email = empleadoInput.email();
        this.celular = empleadoInput.celular();
        this.salario = empleadoInput.salario();
        this.dni = empleadoInput.dni();
        this.fechaRegistro = empleadoInput.fechaRegistro() != null ? empleadoInput.fechaRegistro() : LocalDate.now();
        this.tipoJornada = TipoJornada.fromString(empleadoInput.tipoJornada());
    }
}