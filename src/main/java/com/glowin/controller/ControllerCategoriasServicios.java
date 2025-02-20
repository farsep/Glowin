package com.glowin.controller;

import com.glowin.models.CategoriaServicio;
import com.glowin.models.Input.CategoriaServicioInput;
import com.glowin.models.output.CategoriaServicioOutput;
import com.glowin.repository.ICategoriaServicioRepository;
import com.google.gson.JsonObject;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
    public ResponseEntity<CategoriaServicioOutput> getCategoriaServicio(@PathVariable Long id) {
        Optional<CategoriaServicio> categoriaServicio = categoriaServicioRepository.findById(id);
        return categoriaServicio.map(servicio -> ResponseEntity.ok(new CategoriaServicioOutput(servicio))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Transactional
    @PostMapping
    public ResponseEntity<CategoriaServicioOutput> registerCategoriaServicio(@RequestBody CategoriaServicioInput categoriaServicio) {
        CategoriaServicio categoriaServicio1 = new CategoriaServicio(categoriaServicio);
        categoriaServicioRepository.save(categoriaServicio1);
        return ResponseEntity.ok(new CategoriaServicioOutput(categoriaServicio1));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoriaServicio(@PathVariable Long id) {
        Optional<CategoriaServicio> categoriaServicio = categoriaServicioRepository.findById(id);
        if (categoriaServicio.isPresent()) {
            categoriaServicioRepository.delete(categoriaServicio.get());
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("message", "Category deleted successfully");
            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", "application/json");
            return ResponseEntity.ok().headers(header).body(jsonObject.toString());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}