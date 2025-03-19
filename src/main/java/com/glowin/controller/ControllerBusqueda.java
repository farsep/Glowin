package com.glowin.controller;

import com.glowin.models.Servicio;
import com.glowin.repository.ICategoriaServicioRepository;
import com.glowin.repository.IServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ControllerBusqueda {

    @Autowired
    private IServicioRepository servicioRepository;

    @Autowired
    private ICategoriaServicioRepository categoriaServicioRepository;

    @GetMapping("/search/services")
    public ResponseEntity<Page<Servicio>> searchServices(@RequestParam String query, Pageable pageable) {
        Page<Servicio> servicios = servicioRepository.findByNombreContaining(query, pageable);
        return ResponseEntity.ok(servicios);
    }

//    // Operación para obtener todos los servicios o categorias de servicios que contengan una cadena de texto
//    @GetMapping("/search/servicios-categorias")
//    public ResponseEntity<?> searchServicesOrCategories(@RequestParam String query, Pageable pageable) {
//        Page<Servicio> servicios = servicioRepository.findByNombreContaining(query, pageable);
//        Page<Servicio> categorias = categoriaServicioRepository.findByNombreContaining(query, pageable);
//
//        if (servicios.getContent().isEmpty() && categorias.getContent().isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        return ResponseEntity.ok(Map.of("servicios", servicios, "categorias", categorias));
//    }
}