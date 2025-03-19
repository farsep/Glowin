-- Step 1: Rename the old type
ALTER TYPE estado RENAME TO estado_old;

-- Step 2: Create the new type
CREATE TYPE estado AS ENUM ('CONCLUIDA', 'EN_CURSO', 'CONFIRMADA', 'CANCELADA');

-- Step 3: Update the columns that use the old type to use the new type
ALTER TABLE reservas ALTER COLUMN estado TYPE estado USING estado::text::estado;

-- Step 4: Drop the old type
DROP TYPE estado_old;