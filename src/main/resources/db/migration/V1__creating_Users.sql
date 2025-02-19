DO $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'rol_enum') THEN
            CREATE TYPE rol_enum AS ENUM ('ADMINISTRADOR', 'USUARIO');
        END IF;
    END $$;

DO $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'usuarios') THEN
            CREATE TABLE usuarios (
                                      ID BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                                      nombre VARCHAR(255) NOT NULL,
                                      apellido VARCHAR(255) NOT NULL,
                                      email VARCHAR(255) NOT NULL,
                                      password VARCHAR(255) NOT NULL,
                                      rol rol_enum NOT NULL
            );
        END IF;
    END $$;