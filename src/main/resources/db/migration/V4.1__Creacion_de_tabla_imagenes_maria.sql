CREATE TABLE imagenes_servicios (
                          id SERIAL PRIMARY KEY,          -- Identificador único de la imagen
                          titulo VARCHAR(255) NOT NULL,    -- Título de la imagen
                          descripcion TEXT,                -- Descripción de la imagen (puede ser opcional)
                          url_imagen TEXT NOT NULL,        -- URL de la imagen (campo obligatorio)
                          fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Fecha de creación automática
);