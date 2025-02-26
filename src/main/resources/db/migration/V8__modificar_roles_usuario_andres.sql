-- V8__modificar_roles_usuario_andres.sql

-- Update existing 'USUARIO' values to a new value, e.g., 'CLIENTE'
UPDATE usuarios SET rol = 'CLIENTE' WHERE rol = 'USUARIO';

-- Create a new enum type without the 'USUARIO' value
CREATE TYPE new_rol AS ENUM ('SUPER_ADMINISTRADOR', 'ADMINISTRADOR', 'CLIENTE');

-- Update the columns that use the old enum type to use the new enum type
ALTER TABLE usuarios ALTER COLUMN rol TYPE new_rol USING rol::text::new_rol;

-- Drop the old enum type
DROP TYPE rol;

-- Rename the new enum type to the original name
ALTER TYPE new_rol RENAME TO rol;