-- 1. Eliminar todos los servicios existentes
DELETE FROM servicios;

-- 2. Insertar nuevos servicios con datos corregidos
INSERT INTO servicios (
    nombre,
    descripcion,
    duracion_minutos,
    costo,
    cantidad_sesiones,
    id_categoria
) VALUES
      -- CABELLO
      ('Cortes', 'Mujeres, Hombres, Niños', 45, 30.00, 1,
       (SELECT id FROM categorias_servicios WHERE nombre = 'CABELLO')),
      ('Coloración y tintes', 'Tintes, Mechas, Balayage, Iluminaciones', 120, 80.00, 1,
       (SELECT id FROM categorias_servicios WHERE nombre = 'CABELLO')),
      ('Alisados y tratamientos capilares', 'Keratina, Botox Capilar, Hidratación', 120, 150.00, 1,
       (SELECT id FROM categorias_servicios WHERE nombre = 'CABELLO')),
      ('Peinados y estilizados', 'Ondas, Recogidos, Trenzas, Plancha', 60, 50.00, 1,
       (SELECT id FROM categorias_servicios WHERE nombre = 'CABELLO')),
      ('Extensiones de cabello', 'Cosidas, Clip-in, Micro ring', 90, 200.00, 1,
       (SELECT id FROM categorias_servicios WHERE nombre = 'CABELLO')),

      -- UÑAS
      ('Esmaltado semipermanente', 'Esmaltado con larga duración y brillo', 50, 35.00, 1,
       (SELECT id FROM categorias_servicios WHERE nombre = 'UNIAS')),
      ('Kapping', 'Refuerzo de uñas naturales para mayor resistencia', 60, 40.00, 1,
       (SELECT id FROM categorias_servicios WHERE nombre = 'UNIAS')),
      ('Uñas esculpidas', 'Acrílicas, Gel, Polygel', 90, 60.00, 1,
       (SELECT id FROM categorias_servicios WHERE nombre = 'UNIAS')),
      ('Manicure y pedicure spa', 'Cuidado integral de uñas y piel', 60, 50.00, 1,
       (SELECT id FROM categorias_servicios WHERE nombre = 'UNIAS')),
      ('Diseño de uñas personalizado', 'Arte en uñas a elección del cliente', 75, 55.00, 1,
       (SELECT id FROM categorias_servicios WHERE nombre = 'UNIAS')),

      -- PESTAÑAS
      ('Lifting y rizado de pestañas', 'Moldeado para mayor curvatura y volumen', 60, 45.00, 1,
       (SELECT id FROM categorias_servicios WHERE nombre = 'PESTANIAS')),
      ('Extensiones de pestañas', 'Clásicas, Volumen, Mega Volumen', 90, 100.00, 1,
       (SELECT id FROM categorias_servicios WHERE nombre = 'PESTANIAS')),
      ('Diseños personalizados de pestañas', 'Diseño adaptado a la forma del ojo', 75, 85.00, 1,
       (SELECT id FROM categorias_servicios WHERE nombre = 'PESTANIAS')),
      ('Tintado de pestañas', 'Coloración para realzar la mirada', 45, 40.00, 1,
       (SELECT id FROM categorias_servicios WHERE nombre = 'PESTANIAS')),

      -- CEJAS
      ('Lifting de cejas', 'Levantamiento para efecto más definido', 50, 55.00, 1,
       (SELECT id FROM categorias_servicios WHERE nombre = 'CEJAS')),
      ('Laminado de cejas', 'Alisado y fijación para mayor volumen', 60, 60.00, 1,
       (SELECT id FROM categorias_servicios WHERE nombre = 'CEJAS')),
      ('Perfilado y depilación de cejas', 'Diseño de cejas con precisión', 45, 30.00, 1,
       (SELECT id FROM categorias_servicios WHERE nombre = 'CEJAS')),
      ('Microblading y micropigmentación', 'Técnicas semipermanentes para definición', 120, 180.00, 1,
       (SELECT id FROM categorias_servicios WHERE nombre = 'CEJAS')),

      -- FACIAL & MAQUILLAJE
      ('Maquillaje profesional', 'Social, Novias, Quinceañeras', 90, 100.00, 1,
       (SELECT id FROM categorias_servicios WHERE nombre = 'FACIAL_MAQUILLAJE')),
      ('Limpieza facial profunda', 'Eliminación de impurezas y renovación celular', 75, 70.00, 1,
       (SELECT id FROM categorias_servicios WHERE nombre = 'FACIAL_MAQUILLAJE')),
      ('Peeling químico y dermoabrasión', 'Exfoliación y regeneración de la piel', 60, 90.00, 1,
       (SELECT id FROM categorias_servicios WHERE nombre = 'FACIAL_MAQUILLAJE')),
      ('Hidratación y nutrición facial', 'Tratamiento intensivo para piel seca', 60, 80.00, 1,
       (SELECT id FROM categorias_servicios WHERE nombre = 'FACIAL_MAQUILLAJE')),
      ('Radiofrecuencia facial', 'Reafirmación de la piel mediante ondas', 75, 120.00, 1,
       (SELECT id FROM categorias_servicios WHERE nombre = 'FACIAL_MAQUILLAJE')),
      ('Tratamientos anti-acné y anti-edad', 'Reducción de marcas y líneas de expresión', 90, 150.00, 1,
       (SELECT id FROM categorias_servicios WHERE nombre = 'FACIAL_MAQUILLAJE')),

      -- CUIDADO CORPORAL & DEPILACIÓN
      ('Depilación con cera', 'Facial, Corporal, Íntima', 45, 40.00, 1,
       (SELECT id FROM categorias_servicios WHERE nombre = 'CORPORAL_DEPILACION')),
      ('Depilación láser', 'Eliminación progresiva del vello', 60, 120.00, 6,
       (SELECT id FROM categorias_servicios WHERE nombre = 'CORPORAL_DEPILACION')),
      ('Masajes relajantes y reductores', 'Liberación de estrés y moldeado corporal', 60, 70.00, 1,
       (SELECT id FROM categorias_servicios WHERE nombre = 'CORPORAL_DEPILACION')),
      ('Exfoliación y envolturas corporales', 'Remoción de células muertas y nutrición', 75, 90.00, 1,
       (SELECT id FROM categorias_servicios WHERE nombre = 'CORPORAL_DEPILACION')),

      -- GLOWIN MEN
      ('Corte y estilizado de cabello', 'Corte con técnica profesional y acabado', 45, 25.00, 1,
       (SELECT id FROM categorias_servicios WHERE nombre = 'GLOWIN_MEN')),
      ('Afeitado y perfilado de barba', 'Diseño de barba con afeitado al ras', 30, 20.00, 1,
       (SELECT id FROM categorias_servicios WHERE nombre = 'GLOWIN_MEN')),
      ('Tintes y tratamientos capilares', 'Coloración y cuidado especializado', 90, 80.00, 1,
       (SELECT id FROM categorias_servicios WHERE nombre = 'GLOWIN_MEN')),
      ('Manicure y pedicure masculino', 'Cuidado de uñas y cutículas para hombres', 60, 50.00, 1,
       (SELECT id FROM categorias_servicios WHERE nombre = 'GLOWIN_MEN')),
      ('Cuidado facial y limpieza profunda', 'Eliminación de impurezas y revitalización', 60, 60.00, 1,
       (SELECT id FROM categorias_servicios WHERE nombre = 'GLOWIN_MEN'));
