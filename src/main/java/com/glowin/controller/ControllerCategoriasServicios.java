package com.glowin.controller;

import com.glowin.model.CategoriaServicio;
import com.glowin.model.Input.CategoriaServicioInput;
import com.glowin.model.output.CategoriaServicioOutput;
import com.glowin.repository.ICategoriaServicioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/categorias-servicios")
public class ControllerCategoriasServicios {

    @Autowired
    private ICategoriaServicioRepository categoriaServicioRepository;


    @GetMapping("/all")
    public ResponseEntity<Page<CategoriaServicioOutput>> getAllCategoriasServicios(Pageable pageable) {
        Page<CategoriaServicioOutput> categoriasServicios = categoriaServicioRepository.findAll(pageable).map(CategoriaServicioOutput::new);
        return ResponseEntity.ok(categoriasServicios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaServicioOutput> getCategoriaServicio(Long id) {
        Optional<CategoriaServicio> categoriaServicio = categoriaServicioRepository.findById(id);

        return categoriaServicio.map(servicio -> ResponseEntity.ok(new CategoriaServicioOutput(servicio))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Transactional
    @PostMapping
    public ResponseEntity<CategoriaServicioOutput> registerCategoriaServicio(CategoriaServicioInput categoriaServicio) {
        CategoriaServicio categoriaServicio1 = new CategoriaServicio(categoriaServicio);
        categoriaServicioRepository.save(categoriaServicio1);
        return ResponseEntity.ok(new CategoriaServicioOutput(categoriaServicio1));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoriaServicio(Long id) {
        categoriaServicioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
