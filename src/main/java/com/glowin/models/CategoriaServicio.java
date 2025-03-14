package com.glowin.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.glowin.models.Input.CategoriaServicioInput;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.util.Set;

@Entity
@Table(name = "categorias_servicios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @Column(name = "url_imagen", nullable = false)
    private String urlImagen;

    @JsonManagedReference // Maneja la relación bidireccional con `Servicio`
    @OneToMany(mappedBy = "categoria")
    private Set<Servicio> servicios;

    // Constructor para crear una nueva categoría de servicio
    public CategoriaServicio(CategoriaServicioInput categoriaServicio) {
        this.nombre = categoriaServicio.nombre();
        this.urlImagen = categoriaServicio.urlImagen();
    }
}