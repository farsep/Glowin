package com.glowin.model;

import com.glowin.model.Input.CategoriaServicioInput;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categorias_servicios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long ID;

    private String nombre;

    public CategoriaServicio(CategoriaServicioInput categoriaServicio) {
        this.nombre = categoriaServicio.nombre();
    }
}
