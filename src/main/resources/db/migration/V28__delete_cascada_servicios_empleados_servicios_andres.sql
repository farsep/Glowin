-- Drop the existing foreign key constraint on empleados_servicios for id_servicio
ALTER TABLE empleados_servicios
    DROP CONSTRAINT fk_servicio_empleado_servicio;

-- Add the foreign key constraint with ON DELETE CASCADE for id_servicio
ALTER TABLE empleados_servicios
    ADD CONSTRAINT fk_servicio_empleado_servicio
        FOREIGN KEY (id_servicio)
            REFERENCES servicios(id)
            ON DELETE CASCADE;

-- Drop the existing foreign key constraint on empleados_servicios for id_empleado
ALTER TABLE empleados_servicios
    DROP CONSTRAINT fk_servicio_empleado_empleado;

-- Add the foreign key constraint with ON DELETE CASCADE for id_empleado
ALTER TABLE empleados_servicios
    ADD CONSTRAINT fk_servicio_empleado_empleado
        FOREIGN KEY (id_empleado)
            REFERENCES empleados(id)
            ON DELETE CASCADE;