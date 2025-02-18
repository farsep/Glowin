package com.glowin.controller;

import com.glowin.models.Empleado;
import com.glowin.models.Input.EmpleadoInput;
import com.glowin.models.output.EmpleadoOutput;
import com.glowin.repository.IEmpleadoRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/empleados")
public class ControllerEmpleados {

    @Autowired
    private IEmpleadoRepository empleadoRepository;

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoOutput> getEmpleado(@PathVariable Long id) {
        Optional<Empleado> empleado = empleadoRepository.findById(id);

        if (empleado.isPresent()) {
            return ResponseEntity.ok(new EmpleadoOutput(empleado.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Page<EmpleadoOutput>> getAllEmpleados(Pageable pageable) {
        Page<EmpleadoOutput> empleados = empleadoRepository.findAll(pageable).map(EmpleadoOutput::new);
        return ResponseEntity.ok(empleados);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<EmpleadoOutput> registerEmpleado(@RequestBody EmpleadoInput empleadoInput) {
        Empleado empleado = new Empleado(empleadoInput);
        empleadoRepository.save(empleado);
        return ResponseEntity.ok(new EmpleadoOutput(empleado));
    }

                                                           @DeleteMapping("/{id}")
    public ResponseEntity<EmpleadoOutput> deleteEmpleado(@PathVariable Long id) {
        Optional<Empleado> empleado = empleadoRepository.findById(id);

        if (empleado.isPresent()) {
            empleadoRepository.delete(empleado.get());
            return ResponseEntity.ok(new EmpleadoOutput(empleado.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
