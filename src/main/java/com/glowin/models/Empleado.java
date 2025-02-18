package com.glowin.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal; // para no tener problemas con la precisión en salarios

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empleado {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String celular;
    private BigDecimal salario;
    private String dni;
    private TipoJornada tipoJornada;
}
