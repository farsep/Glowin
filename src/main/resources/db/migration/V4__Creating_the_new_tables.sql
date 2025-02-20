-- Delete all tables except for the flyway_schema_history table
DO $$ DECLARE
    r RECORD;
BEGIN
    FOR r IN (SELECT tablename FROM pg_tables WHERE schemaname = current_schema() AND tablename != 'flyway_schema_history') LOOP
            EXECUTE 'DROP TABLE IF EXISTS ' || quote_ident(r.tablename) || ' CASCADE';
        END LOOP;
END $$;

-- Delete all enums
DO $$ DECLARE
    r RECORD;
BEGIN
    FOR r IN (SELECT typname FROM pg_type WHERE typcategory = 'E') LOOP
            EXECUTE 'DROP TYPE IF EXISTS ' || quote_ident(r.typname);
        END LOOP;
END $$;

-- Create the enums
CREATE TYPE rol AS ENUM ('ADMINISTRADOR', 'USUARIO');
CREATE TYPE tipo_jornada AS ENUM ('MATUTINA', 'VESPERTINA');
CREATE TYPE estado AS ENUM ('PENDIENTE', 'CONFIRMADA', 'CANCELADA');
CREATE TYPE categoria_servicio_enum AS ENUM ('CABELLO', 'UNIAS', 'PESTANIAS', 'FACIAL_MAQUILLAJE', 'CEJAS', 'CORPORAL_DEPILACION', 'GLOWIN_MEN');

-- Create the tables
CREATE TABLE categorias_servicios (
                                      id SERIAL PRIMARY KEY,
                                      nombre categoria_servicio_enum NOT NULL
);

CREATE TABLE usuarios (
                          id SERIAL PRIMARY KEY,
                          nombre VARCHAR(255) NOT NULL,
                          apellido VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL,
                          password VARCHAR(255) NOT NULL,
                          rol rol NOT NULL
);

CREATE TABLE empleados (
                           id SERIAL PRIMARY KEY,
                           nombre VARCHAR(255) NOT NULL,
                           apellido VARCHAR(255) NOT NULL,
                           email VARCHAR(255) NOT NULL,
                           celular VARCHAR(255) NOT NULL,
                           salario NUMERIC(10, 2) NOT NULL,
                           dni VARCHAR(255) NOT NULL,
                           tipo_jornada tipo_jornada NOT NULL
);

CREATE TABLE servicios (
                           id SERIAL PRIMARY KEY,
                           nombre VARCHAR(255) NOT NULL,
                           descripcion TEXT NOT NULL,
                           duracion_minutos INTEGER NOT NULL,
                           costo NUMERIC(30, 2) NOT NULL,
                           id_categoria INTEGER NOT NULL,
                           CONSTRAINT fk_categoria FOREIGN KEY (id_categoria) REFERENCES categorias_servicios(id)
);

CREATE TABLE reservas (
                          id SERIAL PRIMARY KEY,
                          id_cliente INTEGER NOT NULL,
                          id_servicio INTEGER NOT NULL,
                          id_empleado INTEGER NOT NULL,
                          fecha DATE NOT NULL,
                          hora TIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          estado estado NOT NULL,
                          CONSTRAINT fk_cliente FOREIGN KEY (id_cliente) REFERENCES usuarios(id),
                          CONSTRAINT fk_servicio FOREIGN KEY (id_servicio) REFERENCES servicios(id),
                          CONSTRAINT fk_empleado FOREIGN KEY (id_empleado) REFERENCES empleados(id)
);

CREATE TABLE servicio_empleado (
                                   id_servicio INTEGER NOT NULL,
                                   id_empleado INTEGER NOT NULL,
                                   PRIMARY KEY (id_servicio, id_empleado),
                                   CONSTRAINT fk_servicio_empleado_servicio FOREIGN KEY (id_servicio) REFERENCES servicios(id),
                                   CONSTRAINT fk_servicio_empleado_empleado FOREIGN KEY (id_empleado) REFERENCES empleados(id)
);