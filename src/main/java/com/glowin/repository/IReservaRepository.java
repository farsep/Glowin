package com.glowin.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.glowin.models.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IReservaRepository extends JpaRepository<Reserva, Long> {
    Page<Reserva> findAll(Pageable pageable);

    Page<Reserva> findByEmpleadoId(Long id, Pageable pageable);

    Page<Reserva> findByServicioId(Long id, Pageable pageable);

    Page<Reserva> findByFechaBetween(String fechaInicio, String fechaFin, Pageable pageable);

    Page<Reserva> findByClienteId(Long id, Pageable pageable);

    /*@Query("SELECT r FROM Reserva r WHERE r.servicio.id = :idServicio AND r.fecha BETWEEN :fechaInicio AND :fechaFin AND r.estado = 'PENDIENTE'")
    Page<Reserva> findAvailableReservas(Long idServicio, String fechaInicio, String fechaFin);*/
}
