-- Delete all existing rows from the table
DELETE FROM categorias_servicios;

-- Drop the 'nombre' column if it exists
ALTER TABLE categorias_servicios DROP COLUMN IF EXISTS nombre;

-- Add the 'nombre' column with NOT NULL and UNIQUE constraints
ALTER TABLE categorias_servicios ADD COLUMN nombre VARCHAR(255) NOT NULL UNIQUE;

-- Insert new rows into the 'categorias_servicios' table
INSERT INTO categorias_servicios (nombre) VALUES
                                              ('CABELLO'),
                                              ('UNIAS'),
                                              ('PESTANIAS'),
                                              ('FACIAL_MAQUILLAJE'),
                                              ('CEJAS'),
                                              ('CORPORAL_DEPILACION'),
                                              ('GLOWIN_MEN');