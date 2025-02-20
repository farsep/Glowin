package com.glowin.models;

import com.glowin.converter.CategoriaServicioEnumConverter;
import com.glowin.models.Input.CategoriaServicioInput;
import com.glowin.models.enums.CategoriaServicioEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Convert(converter = CategoriaServicioEnumConverter.class)
    @Column(name = "nombre", nullable = false)
    private CategoriaServicioEnum nombre;

    @OneToMany(mappedBy = "categoria")
    private Set<Servicio> servicios;

    public CategoriaServicio(CategoriaServicioInput categoriaServicio) {
        this.nombre = CategoriaServicioEnum.fromString(categoriaServicio.nombre());
    }
}