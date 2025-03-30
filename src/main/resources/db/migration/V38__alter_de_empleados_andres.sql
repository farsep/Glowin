-- Add the columns profesion and url_foto to the table empleados if they do not exist
ALTER TABLE empleados
    ADD COLUMN IF NOT EXISTS profesion VARCHAR(255),
    ADD COLUMN IF NOT EXISTS url_foto VARCHAR(255);

-- Update the existing records with the new values
UPDATE empleados
SET profesion = CASE
                    WHEN nombre = 'Ana' AND apellido = 'Gómez' THEN 'Estilista'
                    WHEN nombre = 'María' AND apellido = 'López' THEN 'Colorista'
                    WHEN nombre = 'Laura' AND apellido = 'Fernández' THEN 'Asesora de imagen'
                    WHEN nombre = 'Paula' AND apellido = 'Martínez' THEN 'Pedicurista'
                    WHEN nombre = 'Elena' AND apellido = 'Sánchez' THEN 'Maquilladora'
                    WHEN nombre = 'Carmen' AND apellido = 'Pérez' THEN 'Especialista en tratamientos capilares'
                    WHEN nombre = 'Andrea' AND apellido = 'Díaz' THEN 'Técnico en extensiones'
                    WHEN nombre = 'Carlos' AND apellido = 'Torres' THEN 'Asesor de imagen'
                    WHEN nombre = 'Miguel' AND apellido = 'Ramírez' THEN 'Estilista'
                    WHEN nombre = 'Lucía' AND apellido = 'Gutiérrez' THEN 'Estilista'
                    WHEN nombre = 'Sofía' AND apellido = 'Jiménez' THEN 'Colorista'
                    WHEN nombre = 'Valeria' AND apellido = 'Ruiz' THEN 'Manicurista'
                    WHEN nombre = 'Natalia' AND apellido = 'Ortega' THEN 'Asesora de imagen'
                    WHEN nombre = 'Diana' AND apellido = 'Castillo' THEN 'Maquilladora'
                    WHEN nombre = 'Gabriela' AND apellido = 'Mendoza' THEN 'Especialista en tratamientos capilares'
                    WHEN nombre = 'Alejandra' AND apellido = 'Vargas' THEN 'Técnico en extensiones'
                    WHEN nombre = 'David' AND apellido = 'Herrera' THEN 'Asesor de imagen'
                    WHEN nombre = 'Javier' AND apellido = 'Ríos' THEN 'Barbero'
                    ELSE profesion
    END,
    url_foto = CASE
                   WHEN nombre = 'Ana' AND apellido = 'Gómez' THEN 'https://iili.io/3AOJ0Cv.jpg'
                   WHEN nombre = 'María' AND apellido = 'López' THEN 'https://iili.io/3AOJ1GR.jpg'
                   WHEN nombre = 'Laura' AND apellido = 'Fernández' THEN 'https://iili.io/3AOJcZJ.jpg'
                   WHEN nombre = 'Paula' AND apellido = 'Martínez' THEN 'https://iili.io/3AOJE4p.jpg'
                   WHEN nombre = 'Elena' AND apellido = 'Sánchez' THEN 'https://iili.io/3AOJM3N.jpg'
                   WHEN nombre = 'Carmen' AND apellido = 'Pérez' THEN 'https://iili.io/3AOdkDg.jpg'
                   WHEN nombre = 'Andrea' AND apellido = 'Díaz' THEN 'https://iili.io/3AOdSWJ.jpg'
                   WHEN nombre = 'Carlos' AND apellido = 'Torres' THEN 'https://iili.io/3AOdtxs.jpg'
                   WHEN nombre = 'Miguel' AND apellido = 'Ramírez' THEN 'https://iili.io/3AOdbsf.jpg'
                   WHEN nombre = 'Lucía' AND apellido = 'Gutiérrez' THEN 'https://iili.io/3AOd4lp.jpg'
                   WHEN nombre = 'Sofía' AND apellido = 'Jiménez' THEN 'https://iili.io/3AOdrfR.jpg'
                   WHEN nombre = 'Valeria' AND apellido = 'Ruiz' THEN 'https://iili.io/3AOd6UN.jpg'
                   WHEN nombre = 'Natalia' AND apellido = 'Ortega' THEN 'https://iili.io/3AOdiJI.jpg'
                   WHEN nombre = 'Diana' AND apellido = 'Castillo' THEN 'https://iili.io/3AOdsRt.jpg'
                   WHEN nombre = 'Gabriela' AND apellido = 'Mendoza' THEN 'https://iili.io/3AOdLOX.jpg'
                   WHEN nombre = 'Alejandra' AND apellido = 'Vargas' THEN 'https://iili.io/3AOdpf4.jpg'
                   WHEN nombre = 'David' AND apellido = 'Herrera' THEN 'https://iili.io/3AOdDWG.jpg'
                   WHEN nombre = 'Javier' AND apellido = 'Ríos' THEN 'https://iili.io/3AOdQbn.jpg'
                   ELSE url_foto
        END;