-- Asignaciones para la categoría CABELLO (2 mujeres)
INSERT INTO empleados_servicios (id_servicio, id_empleado) VALUES
                                                               (1, 10), (1, 11),  -- Cortes
                                                               (2, 10), (2, 11),  -- Coloración y tintes
                                                               (3, 10), (3, 11),  -- Alisados y tratamientos capilares
                                                               (4, 10), (4, 11),  -- Peinados y estilizados
                                                               (5, 10), (5, 11);  -- Extensiones de cabello

-- Asignaciones para la categoría UÑAS (2 mujeres)
INSERT INTO empleados_servicios (id_servicio, id_empleado) VALUES
                                                               (6, 12), (6, 13),  -- Esmaltado semipermanente
                                                               (7, 12), (7, 13),  -- Kapping
                                                               (8, 12), (8, 13),  -- Uñas esculpidas
                                                               (9, 12), (9, 13),  -- Manicure y pedicure spa
                                                               (10, 12), (10, 13); -- Diseño de uñas personalizado

-- Asignaciones para la categoría PESTAÑAS (1 mujer)
INSERT INTO empleados_servicios (id_servicio, id_empleado) VALUES
                                                               (11, 14), -- Lifting y rizado de pestañas
                                                               (12, 14), -- Extensiones de pestañas
                                                               (13, 14), -- Diseños personalizados de pestañas
                                                               (14, 14); -- Tintado de pestañas

-- Asignaciones para la categoría CEJAS (1 mujer)
INSERT INTO empleados_servicios (id_servicio, id_empleado) VALUES
                                                               (15, 14), -- Lifting de cejas
                                                               (16, 14), -- Laminado de cejas
                                                               (17, 14), -- Perfilado y depilación de cejas
                                                               (18, 14); -- Microblading y micropigmentación

-- Asignaciones para la categoría FACIAL_MAQUILLAJE (1 mujer)
INSERT INTO empleados_servicios (id_servicio, id_empleado) VALUES
                                                               (19, 15), -- Maquillaje profesional
                                                               (20, 15), -- Limpieza facial profunda
                                                               (21, 15), -- Peeling químico y dermoabrasión
                                                               (22, 15), -- Hidratación y nutrición facial
                                                               (23, 15), -- Radiofrecuencia facial
                                                               (24, 15); -- Tratamientos anti-acné y anti-edad

-- Asignaciones para la categoría CORPORAL (1 mujer)
INSERT INTO empleados_servicios (id_servicio, id_empleado) VALUES
                                                               (27, 16), -- Masajes relajantes y reductores
                                                               (28, 16); -- Exfoliación y envolturas corporales

-- Asignaciones para la categoría GLOWIN_MEN (2 hombres)
INSERT INTO empleados_servicios (id_servicio, id_empleado) VALUES
                                                               (29, 17), (29, 18), -- Corte y estilizado de cabello
                                                               (30, 17), (30, 18), -- Afeitado y perfilado de barba
                                                               (31, 17), (31, 18); -- Tintes y tratamientos capilares

-- Excepciones: Asignar a las mujeres de las categorías relevantes los servicios de GLOWIN_MEN que coinciden
INSERT INTO empleados_servicios (id_servicio, id_empleado) VALUES
                                                               (32, 12), (32, 13), -- Manicure y pedicure masculino -> Uñas
                                                               (33, 14), (33, 15); -- Cuidado facial y limpieza profunda -> Pestañas, Cejas, Facial, Corporal

