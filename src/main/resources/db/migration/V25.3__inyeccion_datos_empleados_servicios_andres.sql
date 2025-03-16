-- Asignaciones para la categoría CABELLO (2 mujeres)
INSERT INTO empleados_servicios (id_servicio, id_empleado) VALUES
                                                               (1, 1), (1, 2),  -- Cortes
                                                               (2, 1), (2, 2),  -- Coloración y tintes
                                                               (3, 1), (3, 2),  -- Alisados y tratamientos capilares
                                                               (4, 1), (4, 2),  -- Peinados y estilizados
                                                               (5, 1), (5, 2);  -- Extensiones de cabello

-- Asignaciones para la categoría UÑAS (2 mujeres)
INSERT INTO empleados_servicios (id_servicio, id_empleado) VALUES
                                                               (6, 3), (6, 4),  -- Esmaltado semipermanente
                                                               (7, 3), (7, 4),  -- Kapping
                                                               (8, 3), (8, 4),  -- Uñas esculpidas
                                                               (9, 3), (9, 4),  -- Manicure y pedicure spa
                                                               (10, 3), (10, 4); -- Diseño de uñas personalizado

-- Asignaciones para la categoría PESTAÑAS (1 mujer)
INSERT INTO empleados_servicios (id_servicio, id_empleado) VALUES
                                                               (11, 5), -- Lifting y rizado de pestañas
                                                               (12, 5), -- Extensiones de pestañas
                                                               (13, 5), -- Diseños personalizados de pestañas
                                                               (14, 5); -- Tintado de pestañas

-- Asignaciones para la categoría CEJAS (1 mujer)
INSERT INTO empleados_servicios (id_servicio, id_empleado) VALUES
                                                               (15, 5), -- Lifting de cejas
                                                               (16, 5), -- Laminado de cejas
                                                               (17, 5), -- Perfilado y depilación de cejas
                                                               (18, 5); -- Microblading y micropigmentación

-- Asignaciones para la categoría FACIAL_MAQUILLAJE (1 mujer)
INSERT INTO empleados_servicios (id_servicio, id_empleado) VALUES
                                                               (19, 6), -- Maquillaje profesional
                                                               (20, 6), -- Limpieza facial profunda
                                                               (21, 6), -- Peeling químico y dermoabrasión
                                                               (22, 6), -- Hidratación y nutrición facial
                                                               (23, 6), -- Radiofrecuencia facial
                                                               (24, 6); -- Tratamientos anti-acné y anti-edad

-- Asignaciones para la categoría CORPORAL (1 mujer)
INSERT INTO empleados_servicios (id_servicio, id_empleado) VALUES
                                                               (27, 7), -- Masajes relajantes y reductores
                                                               (28, 7); -- Exfoliación y envolturas corporales

-- Asignaciones para la categoría GLOWIN_MEN (2 hombres)
INSERT INTO empleados_servicios (id_servicio, id_empleado) VALUES
                                                               (29, 8), (29, 9), -- Corte y estilizado de cabello
                                                               (30, 8), (30, 9), -- Afeitado y perfilado de barba
                                                               (31, 8), (31, 9); -- Tintes y tratamientos capilares

-- Excepciones: Asignar a las mujeres de las categorías relevantes los servicios de GLOWIN_MEN que coinciden
INSERT INTO empleados_servicios (id_servicio, id_empleado) VALUES
                                                               (32, 3), (32, 4), -- Manicure y pedicure masculino -> Uñas
                                                               (33, 5), (33, 6); -- Cuidado facial y limpieza profunda -> Pestañas, Cejas, Facial, Corporal

