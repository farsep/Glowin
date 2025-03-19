-- Drop the existing foreign key constraint on favoritos for id_servicio
ALTER TABLE favoritos
    DROP CONSTRAINT fk_servicio;

-- Add the foreign key constraint with ON DELETE CASCADE for id_servicio
ALTER TABLE favoritos
    ADD CONSTRAINT fk_servicio
        FOREIGN KEY (id_servicio)
            REFERENCES servicios(id)
            ON DELETE CASCADE;