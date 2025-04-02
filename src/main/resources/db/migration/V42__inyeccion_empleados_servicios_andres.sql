-- Inserción del empleado Farid en la tabla empleados
INSERT INTO empleados (nombre, apellido, email, celular, salario, dni, tipo_jornada, profesion, url_foto)
VALUES ('Farid', 'Apellido', 'farid.email@example.com', '223456788', 10.00, '22345678J', 'VESPERTINA', 'Barbero y Estilista', 'https://iili.io/3RhTYmX.jpg');

-- Asignaciones de servicios para el empleado Farid (servicios del 1 al 33)
INSERT INTO empleados_servicios (id_servicio, id_empleado)
VALUES
    (1, (SELECT id FROM empleados WHERE email = 'farid.email@example.com')),
    (2, (SELECT id FROM empleados WHERE email = 'farid.email@example.com')),
    (3, (SELECT id FROM empleados WHERE email = 'farid.email@example.com')),
    (4, (SELECT id FROM empleados WHERE email = 'farid.email@example.com')),
    (5, (SELECT id FROM empleados WHERE email = 'farid.email@example.com')),
    (6, (SELECT id FROM empleados WHERE email = 'farid.email@example.com')),
    (7, (SELECT id FROM empleados WHERE email = 'farid.email@example.com')),
    (8, (SELECT id FROM empleados WHERE email = 'farid.email@example.com')),
    (9, (SELECT id FROM empleados WHERE email = 'farid.email@example.com')),
    (10, (SELECT id FROM empleados WHERE email = 'farid.email@example.com')),
    (11, (SELECT id FROM empleados WHERE email = 'farid.email@example.com')),
    (12, (SELECT id FROM empleados WHERE email = 'farid.email@example.com')),
    (13, (SELECT id FROM empleados WHERE email = 'farid.email@example.com')),
    (14, (SELECT id FROM empleados WHERE email = 'farid.email@example.com')),
    (15, (SELECT id FROM empleados WHERE email = 'farid.email@example.com')),
    (16, (SELECT id FROM empleados WHERE email = 'farid.email@example.com')),
    (17, (SELECT id FROM empleados WHERE email = 'farid.email@example.com')),
    (18, (SELECT id FROM empleados WHERE email = 'farid.email@example.com')),
    (19, (SELECT id FROM empleados WHERE email = 'farid.email@example.com')),
    (20, (SELECT id FROM empleados WHERE email = 'farid.email@example.com')),
    (21, (SELECT id FROM empleados WHERE email = 'farid.email@example.com')),
    (22, (SELECT id FROM empleados WHERE email = 'farid.email@example.com')),
    (23, (SELECT id FROM empleados WHERE email = 'farid.email@example.com')),
    (24, (SELECT id FROM empleados WHERE email = 'farid.email@example.com')),
    (25, (SELECT id FROM empleados WHERE email = 'farid.email@example.com')),
    (26, (SELECT id FROM empleados WHERE email = 'farid.email@example.com')),
    (27, (SELECT id FROM empleados WHERE email = 'farid.email@example.com')),
    (28, (SELECT id FROM empleados WHERE email = 'farid.email@example.com')),
    (29, (SELECT id FROM empleados WHERE email = 'farid.email@example.com')),
    (30, (SELECT id FROM empleados WHERE email = 'farid.email@example.com')),
    (31, (SELECT id FROM empleados WHERE email = 'farid.email@example.com')),
    (32, (SELECT id FROM empleados WHERE email = 'farid.email@example.com')),
    (33, (SELECT id FROM empleados WHERE email = 'farid.email@example.com'));