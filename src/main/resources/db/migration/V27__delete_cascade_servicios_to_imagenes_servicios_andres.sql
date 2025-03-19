-- Drop the existing foreign key constraint
ALTER TABLE imagenes_servicios
    DROP CONSTRAINT fk_servicio_imagen;

-- Add the foreign key constraint with ON DELETE CASCADE
ALTER TABLE imagenes_servicios
    ADD CONSTRAINT fk_servicio_imagen
        FOREIGN KEY (id_servicio)
            REFERENCES servicios(id)
            ON DELETE CASCADE;