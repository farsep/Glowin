package com.glowin.model.update;

import com.glowin.model.enums.TipoJornada;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmpleadoUpdate {
    private String nombre;
    private String apellido;
    private String email;
    private String celular;
    private BigDecimal salario;
    private String dni;
    private TipoJornada tipoJornada;
}
