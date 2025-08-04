-- Insertar permisos básicos
INSERT INTO permissions (name, description, resource, action) VALUES
('STUDENT_READ', 'Read student information', 'STUDENT', 'READ'),
('STUDENT_WRITE', 'Create and update students', 'STUDENT', 'WRITE'),
('STUDENT_DELETE', 'Delete students', 'STUDENT', 'DELETE'),
('USER_ADMIN', 'Full user administration', 'USER', '*');

-- Insertar roles
INSERT INTO roles (name, description) VALUES
('USER', 'Regular user with read access'),
('ADMIN', 'Administrator with full access');

-- Asignar permisos a roles
INSERT INTO role_permissions (role_id, permission_id) VALUES
(1, 1), -- USER -> STUDENT_READ
(2, 1), -- ADMIN -> STUDENT_READ
(2, 2), -- ADMIN -> STUDENT_WRITE
(2, 3), -- ADMIN -> STUDENT_DELETE
(2, 4); -- ADMIN -> USER_ADMIN

-- Crear usuarios de ejemplo 
-- NOTA: Las contraseñas se encriptarán automáticamente por el UserService
-- NO insertar directamente para evitar doble encriptación

-- Los roles se asignarán automáticamente por el DataInitializer

-- Insertar estudiantes de ejemplo
INSERT INTO students (first_name, last_name, age, address) VALUES
('Juan', 'Sanz', 22, 'Calle 123'),
('María', 'García', 21, 'Avenida Principal 456'),
('Carlos', 'López', 23, 'Plaza Central 789'),
('Ana', 'Martínez', 20, 'Calle Segunda 321'),
('Pedro', 'Rodríguez', 24, 'Boulevard Norte 654');