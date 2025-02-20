package com.glowin.models;


import com.glowin.models.Input.UsuarioInput;
import com.glowin.models.enums.Rol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.util.Set;

@Entity
@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String nombre;
    private String apellido;
    private String email;
    private String password;

    @Enumerated
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "rol")
    private Rol rol;

    @OneToMany(mappedBy = "cliente")
    private Set<Reserva> reservas;

    public Usuario(UsuarioInput usuarioInput) {
        this.nombre = usuarioInput.nombre();
        this.apellido = usuarioInput.apellido();
        this.email = usuarioInput.email();
        this.password = usuarioInput.password();
        this.rol = Rol.fromString(usuarioInput.rol());
    }
}