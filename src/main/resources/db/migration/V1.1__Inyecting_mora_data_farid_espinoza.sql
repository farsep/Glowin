--Inyectando mas usuarios a la base de datos, tabla usuarios, de forma recursiva con datos aleatorios
DO $$
    DECLARE
        i INTEGER := 1;
    BEGIN
        WHILE i <= 100 LOOP
                INSERT INTO usuarios (nombre, apellido, email, password, rol)
                VALUES (
                           'nombre' || i,
                           'apellido' || i,
                           'email' || i || '@example.com',
                           'password' || i,
                           'USUARIO'
                       );
                i := i + 1;
            END LOOP;
    END $$;
