package com.glowin.controller;

import com.glowin.models.CategoriaServicio;
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

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class ControllerBusqueda {

    @Autowired
    private IServicioRepository servicioRepository;

    @Autowired
    private ICategoriaServicioRepository categoriaServicioRepository;

    @GetMapping("/search/services")
    public ResponseEntity<Page<Servicio>> searchServices(@RequestParam String query, Pageable pageable) {
        Page<Servicio> servicios = servicioRepository.findByNombreContainingIgnoreCase(query, pageable);
        return ResponseEntity.ok(servicios);
    }

/*    // Operación para obtener todos los servicios o categorias de servicios que contengan una cadena de texto
    @GetMapping("/search/servicios-categorias")
    public ResponseEntity<?> searchServicesOrCategories(@RequestParam String query, Pageable pageable) {
        Page<Servicio> servicios = servicioRepository.findByNombreContaining(query, pageable);
        Page<CategoriaServicio> categorias = categoriaServicioRepository.findByNombreContaining(query, pageable);

        List<Servicio> servicioList = new CopyOnWriteArrayList<>(servicios.getContent());
        List<CategoriaServicio> categoriaList = new CopyOnWriteArrayList<>(categorias.getContent());

        if (servicioList.isEmpty() && categoriaList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(Map.of("servicios", servicioList, "categorias", categoriaList));
    }*/
}