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

    // Este metodo es necesario para poder obtener los empleados de un servicio y su tipo de jornada
    @Query(value = "SELECT e.* FROM empleados e " +
            "JOIN empleados_servicios es ON e.id = es.id_empleado " +
            "WHERE es.id_servicio = :idServicio AND e.tipo_jornada = CAST(:tipoJornada AS tipo_jornada)", nativeQuery = true)
    List<Empleado> findEmpleadosByServicioAndTipoJornada(@Param("idServicio") Long idServicio, @Param("tipoJornada") String tipoJornada);

}
