DELETE FROM usuarios;
ALTER TABLE usuarios ADD CONSTRAINT unique_email UNIQUE (email);
INSERT INTO usuarios (nombre, apellido, email, celular, password, rol, fecha_registro, hora_registro) VALUES
    ('Super', 'Admin', 'superadmin@example.com', '123456789', 'hashedpassword123', 'SUPER_ADMINISTRADOR', CURRENT_DATE, CURRENT_TIME);
INSERT INTO usuarios (nombre, apellido, email, celular, password, rol, fecha_registro, hora_registro) VALUES
                                                                                                          ('Admin1', 'User', 'admin1@example.com', '123456781', 'hashedpassword123', 'ADMINISTRADOR', CURRENT_DATE, CURRENT_TIME),
                                                                                                          ('Admin2', 'User', 'admin2@example.com', '123456782', 'hashedpassword123', 'ADMINISTRADOR', CURRENT_DATE, CURRENT_TIME),
                                                                                                          ('Admin3', 'User', 'admin3@example.com', '123456783', 'hashedpassword123', 'ADMINISTRADOR', CURRENT_DATE, CURRENT_TIME);
INSERT INTO usuarios (nombre, apellido, email, celular, password, rol, fecha_registro, hora_registro) VALUES
                                                                                                          ('Cliente1', 'User', 'cliente1@example.com', '123456784', 'hashedpassword123', 'CLIENTE', CURRENT_DATE, CURRENT_TIME),
                                                                                                          ('Cliente2', 'User', 'cliente2@example.com', '123456785', 'hashedpassword123', 'CLIENTE', CURRENT_DATE, CURRENT_TIME),
                                                                                                          ('Cliente3', 'User', 'cliente3@example.com', '123456786', 'hashedpassword123', 'CLIENTE', CURRENT_DATE, CURRENT_TIME),
                                                                                                          ('Cliente4', 'User', 'cliente4@example.com', '123456787', 'hashedpassword123', 'CLIENTE', CURRENT_DATE, CURRENT_TIME);