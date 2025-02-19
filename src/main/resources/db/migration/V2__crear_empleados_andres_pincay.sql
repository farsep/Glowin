CREATE TYPE tipo_jornada_enum AS ENUM ('MATUTINA', 'VESPERTINA');

CREATE TABLE empleados (
                           id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                           nombre VARCHAR(255) NOT NULL,
                           apellido VARCHAR(255) NOT NULL,
                           email VARCHAR(255) NOT NULL,
                           celular VARCHAR(255) NOT NULL,
                           salario DECIMAL(19, 2) NOT NULL,
                           dni VARCHAR(255) NOT NULL,
                           tipoJornada tipo_jornada_enum NOT NULL
);

-- Inyeccion de  datos de prueba
INSERT INTO empleados (nombre, apellido, email, celular, salario, dni, tipoJornada)
VALUES
    ('Juan', 'Perez', 'juan.perez@example.com', '123456789', 3000.00, '12345678A', 'MATUTINA'),
    ('Maria', 'Gomez', 'maria.gomez@example.com', '987654321', 3500.00, '87654321B', 'VESPERTINA');