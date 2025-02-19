DO $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'tipo_jornada_enum') THEN
            CREATE TYPE tipo_jornada_enum AS ENUM ('MATUTINA', 'VESPERTINA');
        END IF;
    END $$;

DO $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'empleados') THEN
            CREATE TABLE empleados (
                                       ID BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                                       nombre VARCHAR(255) NOT NULL,
                                       apellido VARCHAR(255) NOT NULL,
                                       email VARCHAR(255) NOT NULL,
                                       celular VARCHAR(255) NOT NULL,
                                       salario DECIMAL(19, 2) NOT NULL,
                                       dni VARCHAR(255) NOT NULL,
                                       tipoJornada tipo_jornada_enum NOT NULL
            );
        END IF;
    END $$;

-- Inyeccion de datos de prueba
INSERT INTO empleados (nombre, apellido, email, celular, salario, dni, tipoJornada)
VALUES
    ('Juan', 'Perez', 'juan.perez@example.com', '123456789', 3000.00, '12345678A', 'MATUTINA'),
    ('Maria', 'Gomez', 'maria.gomez@example.com', '987654321', 3500.00, '87654321B', 'VESPERTINA');