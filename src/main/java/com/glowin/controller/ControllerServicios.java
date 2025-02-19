package com.glowin.controller;

import com.glowin.model.Input.ServicioInput;
import com.glowin.model.Servicio;
import com.glowin.model.output.ServicioOutput;
import com.glowin.repository.IServicioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/servicios")
public class ControllerServicios {

    @Autowired
    private IServicioRepository servicioRepository;

    @GetMapping("/{id}")
    public ResponseEntity<ServicioOutput> getServicio(@PathVariable Long id) {
        Optional<Servicio> servicio = servicioRepository.findById(id);
        if (servicio.isPresent()) {
            return ResponseEntity.ok(new ServicioOutput(servicio.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    @PostMapping
    public ResponseEntity<ServicioOutput> registerServicio(@RequestBody ServicioInput servicioInput) {
        Servicio servicio = new Servicio(servicioInput);
        servicioRepository.save(servicio);
        return ResponseEntity.ok(new ServicioOutput(servicio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicio(@PathVariable Long id) {
        servicioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
