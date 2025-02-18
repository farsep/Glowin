package com.glowin.controller;

import com.glowin.models.Input.ReservaInput;
import com.glowin.models.Reserva;
import com.glowin.models.output.ReservaOutput;
import com.glowin.repository.IEmpleadoRepository;
import com.glowin.repository.IReservaRepository;
import com.glowin.repository.IServicioRepository;
import com.glowin.repository.IUsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/reservas")
public class ControllerReservas {

    @Autowired
    private IReservaRepository reservaRepo;

    @Autowired
    private IEmpleadoRepository empleadoRepo;

    @Autowired
    private IUsuarioRepository usuarioRepo;

    @Autowired
    private IServicioRepository servicioRepo;




    @GetMapping("/all")
    public ResponseEntity<Page<ReservaOutput>> getAllReservas(Pageable pageable) {
        Page<ReservaOutput> reservas = reservaRepo.findAll(pageable).map(this::ConvertToOutput);
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaOutput> getReserva(Long id) {
        Optional<Reserva> reserva = reservaRepo.findById(id);

        return reserva.map(value -> ResponseEntity.ok(ConvertToOutput(value))).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @Transactional
    @PostMapping
    public ResponseEntity<ReservaOutput> registerReserva(ReservaInput reserva) {
        Reserva reserva1 = new Reserva(reserva);
        reservaRepo.save(reserva1);
        return ResponseEntity.ok(ConvertToOutput(reserva1));
    }


    public ReservaOutput ConvertToOutput(Reserva reserva){
        return new ReservaOutput(reserva, empleadoRepo.findById(reserva.getIdEmpleado()).orElseThrow(), usuarioRepo.findById(reserva.getIdCliente()).orElseThrow(), servicioRepo.findById(reserva.getIdServicio()).orElseThrow());
    }

}
