package com.glowin.models;


import com.glowin.models.Input.UsuarioInput;
import com.glowin.models.enums.Rol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuarios", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Check(constraints = "rol != 'SUPER_ADMINISTRADOR' OR (SELECT COUNT(*) FROM usuarios WHERE rol = 'SUPER_ADMINISTRADOR') <= 1")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String nombre;
    private String apellido;

    @Column(unique = true, nullable = false)
    private String email;
    private String celular;
    private String password;

    @Enumerated
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "rol")
    private Rol rol;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    @Column(name = "hora_registro", nullable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalTime horaRegistro;

    @OneToMany(mappedBy = "cliente")
    private Set<Reserva> reservas;

    public Usuario(UsuarioInput usuarioInput) {
        this.nombre = usuarioInput.nombre();
        this.apellido = usuarioInput.apellido();
        this.email = usuarioInput.email();
        this.password = usuarioInput.password();
        this.celular = usuarioInput.celular();
        this.rol = Rol.fromString(usuarioInput.rol());
        this.fechaRegistro = LocalDate.now();
        this.horaRegistro = LocalTime.now();

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rol.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}