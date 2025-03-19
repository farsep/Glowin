package com.glowin.repository;

import com.glowin.models.Servicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IServicioRepository extends JpaRepository<Servicio, Long> {

    // Este metodo es necesario para poder obtener los empleados de un servicio
    @Query(value = "SELECT es.id_empleado FROM empleados_servicios es WHERE es.id_servicio = :idServicio", nativeQuery = true)
    List<Long> findEmpleadoIdsByServicioId(@Param("idServicio") Long idServicio);

    Page<Servicio> findByNombreContainingIgnoreCase(String query, Pageable pageable);
}
