CREATE TABLE favoritos (
                           id SERIAL PRIMARY KEY,
                           id_usuario BIGINT NOT NULL,
                           id_servicio BIGINT NOT NULL,
                           fecha_agregado DATE NOT NULL DEFAULT CURRENT_DATE,
                           CONSTRAINT fk_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
                           CONSTRAINT fk_servicio FOREIGN KEY (id_servicio) REFERENCES servicios(id),
                           CONSTRAINT unique_usuario_servicio UNIQUE (id_usuario, id_servicio)
);

INSERT INTO favoritos (id_usuario, id_servicio) VALUES
                                                    (5, 1),
                                                    (5, 2),
                                                    (6, 3),
                                                    (6, 4),
                                                    (7, 5),
                                                    (7, 6),
                                                    (8, 7),
                                                    (8, 8);