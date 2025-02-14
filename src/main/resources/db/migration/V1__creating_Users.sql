CREATE TYPE rol_enum AS ENUM ('ADMINISTRATOR', 'USUARIO');

CREATE TABLE Usuario (
                         id BIGINT PRIMARY KEY,
                         nombre VARCHAR(255) NOT NULL,
                         apellido VARCHAR(255) NOT NULL,
                         email VARCHAR(255) NOT NULL,
                         password VARCHAR(255) NOT NULL,
                         rol rol_enum NOT NULL
);

-- Inyectar datos de prueba
INSERT INTO Usuario (id, nombre, apellido, email, password, rol) VALUES (1, 'admin', 'admin', 'pepe@tubi.com', 'admin', 'ADMINISTRATOR');