package com.glowin.repository;

import com.glowin.model.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReservaRepository extends JpaRepository<Reserva, Long> {
    Page<Reserva> findAll(Pageable pageable);
}
