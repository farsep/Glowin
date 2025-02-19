package com.glowin.model;

import com.glowin.model.Input.UsuarioInput;
import com.glowin.model.enums.Rol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Rol rol;

    public Usuario(String nombre, String apellido,String email,String password,Rol rol)
    {
        this.nombre=nombre;
        this.apellido=apellido;
        this.email=email;
        this.password=password;
        this.rol=rol;
    }

    public Usuario(UsuarioInput usuarioInput) {
        this.nombre = usuarioInput.nombre();
        this.apellido = usuarioInput.apellido();
        this.email = usuarioInput.email();
        this.password = usuarioInput.password();
        this.rol = Rol.fromString(usuarioInput.rol());
    }
}
