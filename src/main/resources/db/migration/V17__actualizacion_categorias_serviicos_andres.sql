-- Add the 'url_imagen' column with NOT NULL constraint if it does not exist
ALTER TABLE categorias_servicios ADD COLUMN IF NOT EXISTS url_imagen VARCHAR(255) NOT NULL DEFAULT '';

-- Update existing rows with the new 'url_imagen' values
UPDATE categorias_servicios SET url_imagen = 'url_cabello' WHERE nombre = 'CABELLO';
UPDATE categorias_servicios SET url_imagen = 'url_unias' WHERE nombre = 'UNIAS';
UPDATE categorias_servicios SET url_imagen = 'url_pestanias' WHERE nombre = 'PESTANIAS';
UPDATE categorias_servicios SET url_imagen = 'url_facial_maquillaje' WHERE nombre = 'FACIAL_MAQUILLAJE';
UPDATE categorias_servicios SET url_imagen = 'url_cejas' WHERE nombre = 'CEJAS';
UPDATE categorias_servicios SET url_imagen = 'url_corporal_depilacion' WHERE nombre = 'CORPORAL_DEPILACION';
UPDATE categorias_servicios SET url_imagen = 'url_glowin_men' WHERE nombre = 'GLOWIN_MEN';

-- Insert new rows into the 'categorias_servicios' table if they do not already exist
INSERT INTO categorias_servicios (nombre, url_imagen) VALUES
                                                          ('CABELLO', 'url_cabello'),
                                                          ('UNIAS', 'url_unias'),
                                                          ('PESTANIAS', 'url_pestanias'),
                                                          ('FACIAL_MAQUILLAJE', 'url_facial_maquillaje'),
                                                          ('CEJAS', 'url_cejas'),
                                                          ('CORPORAL_DEPILACION', 'url_corporal_depilacion'),
                                                          ('GLOWIN_MEN', 'url_glowin_men')
ON CONFLICT (nombre) DO NOTHING;