-- Eliminar la columna id_categoria y su constraint
ALTER TABLE imagenes_servicios
    DROP CONSTRAINT IF EXISTS fk_categoria_imagen,
    DROP COLUMN IF EXISTS id_categoria;

-- Agregar la columna id_servicio y su constraint
ALTER TABLE imagenes_servicios
    ADD COLUMN id_servicio INTEGER,
    ADD CONSTRAINT fk_servicio_imagen
        FOREIGN KEY (id_servicio)
            REFERENCES servicios(id);