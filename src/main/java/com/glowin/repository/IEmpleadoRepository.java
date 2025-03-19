package com.glowin.repository;

import com.glowin.models.Empleado;
import com.glowin.models.Servicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface IEmpleadoRepository extends JpaRepository<Empleado, Long> {
    Page<Empleado> findAll(Pageable pageable);

    // Este metodo es necesario para poder obtener los servicios de un empleado
    @Query(value = "SELECT es.id_servicio FROM empleados_servicios es WHERE es.id_empleado = :idEmpleado", nativeQuery = true)
    List<Long> findServicioIdsByEmpleadoId(@Param("idEmpleado") Long idEmpleado);

}
