ALTER TABLE imagenes_servicios
    ADD COLUMN id_categoria INTEGER,
    ADD CONSTRAINT fk_categoria_imagen
    FOREIGN KEY (id_categoria)
    REFERENCES categorias_servicios(id);