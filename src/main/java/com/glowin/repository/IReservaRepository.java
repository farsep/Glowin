package com.glowin.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.glowin.models.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface IReservaRepository extends JpaRepository<Reserva, Long> {
    Page<Reserva> findAll(Pageable pageable);

    Page<Reserva> findByEmpleadoId(Long id, Pageable pageable);

    Page<Reserva> findByServicioId(Long id, Pageable pageable);

    Page<Reserva> findByFechaBetween(LocalDate fecha, LocalDate fecha2, Pageable pageable);

    Page<Reserva> findByClienteId(Long id, Pageable pageable);

    Page<Reserva> findByFechaBetweenAndServicioId(LocalDate parse, LocalDate parse1, Long idServicio, Pageable pageable);
}
