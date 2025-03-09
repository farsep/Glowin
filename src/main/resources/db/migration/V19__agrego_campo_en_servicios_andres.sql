ALTER TABLE servicios ADD COLUMN url_imagen VARCHAR NOT NULL DEFAULT 'url';

UPDATE servicios SET url_imagen = 'url';