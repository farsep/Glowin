package com.glowin.models;

import com.glowin.models.Input.EmpleadoInput;
import com.glowin.models.enums.TipoJornada;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
    private TipoJornada tipoJornada;

    @ManyToMany(mappedBy = "empleados")
    private Set<Servicio> servicios;

    public Empleado(String nombre, String apellido, String email, String celular, BigDecimal salario, String dni, TipoJornada tipoJornada) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.celular = celular;
        this.salario = salario;
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
        this.tipoJornada = TipoJornada.fromString(empleadoInput.tipoJornada());
    }
}