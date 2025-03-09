-- Step 1: Add the column without the NOT NULL constraint
ALTER TABLE servicios ADD COLUMN nombre_categoria VARCHAR;

-- Step 2: Update the existing records with the names of the categories
UPDATE servicios s
SET nombre_categoria = (
    SELECT c.nombre
    FROM categorias_servicios c
    WHERE c.id = s.id_categoria
);

-- Step 3: Alter the column to add the NOT NULL constraint
ALTER TABLE servicios ALTER COLUMN nombre_categoria SET NOT NULL;