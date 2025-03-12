-- Version: 23
ALTER TABLE servicios
    DROP CONSTRAINT fk_categoria;

-- Run on delete cascade
ALTER TABLE servicios
    ADD CONSTRAINT fk_categoria
        FOREIGN KEY (id_categoria)
            REFERENCES categorias_servicios(id)
            ON DELETE CASCADE;