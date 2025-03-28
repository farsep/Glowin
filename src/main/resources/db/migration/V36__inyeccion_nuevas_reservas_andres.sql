DELETE FROM reservas;

INSERT INTO reservas (id_cliente, id_servicio, id_empleado, fecha, hora, estado, fecha_creacion, hora_creacion) VALUES
                                                                                                                    (5, 1, 11, '2025-03-21', '09:00:00', 'CONFIRMADA', CURRENT_DATE, CURRENT_TIME),
                                                                                                                    (6, 1, 11, '2025-03-21', '10:00:00', 'CONFIRMADA', CURRENT_DATE, CURRENT_TIME),
                                                                                                                    (7, 1, 11, '2025-03-21', '11:00:00', 'CONFIRMADA', CURRENT_DATE, CURRENT_TIME),
                                                                                                                    (8, 1, 11, '2025-03-21', '12:00:00', 'CONFIRMADA', CURRENT_DATE, CURRENT_TIME),
                                                                                                                    (5, 1, 11, '2025-03-21', '13:00:00', 'CONFIRMADA', CURRENT_DATE, CURRENT_TIME),
                                                                                                                    (6, 1, 11, '2025-03-21', '14:00:00', 'CONFIRMADA', CURRENT_DATE, CURRENT_TIME),
                                                                                                                    (7, 1, 11, '2025-03-21', '15:00:00', 'CONFIRMADA', CURRENT_DATE, CURRENT_TIME),
                                                                                                                    (8, 1, 11, '2025-03-21', '16:00:00', 'CONFIRMADA', CURRENT_DATE, CURRENT_TIME),
                                                                                                                    (5, 1, 11, '2025-03-21', '17:00:00', 'CONFIRMADA', CURRENT_DATE, CURRENT_TIME),
                                                                                                                    (6, 1, 11, '2025-03-21', '18:00:00', 'CONFIRMADA', CURRENT_DATE, CURRENT_TIME),
                                                                                                                    (7, 1, 11, '2025-03-21', '19:00:00', 'CONFIRMADA', CURRENT_DATE, CURRENT_TIME),
                                                                                                                    (8, 1, 11, '2025-03-21', '20:00:00', 'CONFIRMADA', CURRENT_DATE, CURRENT_TIME);

-- Agregar más reservas con id_servicios del 2 al 33, horas aleatorias y fechas entre 2025-04-05 y 2025-05-05
DO $$
    BEGIN
        FOR id_servicio IN 2..33 LOOP
                FOR i IN 1..10 LOOP
                        INSERT INTO reservas (id_cliente, id_servicio, id_empleado, fecha, hora, estado, fecha_creacion, hora_creacion)
                        VALUES (
                                   (SELECT id FROM usuarios ORDER BY RANDOM() LIMIT 1),
                                   id_servicio,
                                   (SELECT id FROM empleados ORDER BY RANDOM() LIMIT 1),
                                   (SELECT DATE '2025-04-05' + (RANDOM() * (DATE '2025-05-05' - DATE '2025-04-05'))::INT),
                                   (SELECT TIME '09:00:00' + (RANDOM() * (TIME '20:00:00' - TIME '09:00:00'))),
                                   'CONFIRMADA',
                                   CURRENT_DATE,
                                   CURRENT_TIME
                               );
                    END LOOP;
            END LOOP;
    END $$;