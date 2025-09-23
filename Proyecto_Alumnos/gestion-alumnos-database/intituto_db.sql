-- Crear la base de datos
CREATE DATABASE instituto_db;

-- Conectarse a la base de datos
\c instituto_db;

-- Crear la tabla de alumnos
CREATE TABLE alumnos (
    id_alumno SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    correo VARCHAR(150) UNIQUE NOT NULL,
    materia VARCHAR(100) NOT NULL
);

-- Insertar algunos datos de ejemplo
INSERT INTO alumnos (nombre, apellido, correo, materia) VALUES
('Juan', 'Pérez', 'juan.perez@email.com', 'Matemáticas'),
('María', 'González', 'maria.gonzalez@email.com', 'Física'),
('Carlos', 'Rodríguez', 'carlos.rodriguez@email.com', 'Química'),
('Ana', 'Martínez', 'ana.martinez@email.com', 'Historia'),
('Luis', 'García', 'luis.garcia@email.com', 'Programación');

-- Crear índice para mejorar las búsquedas por correo
CREATE INDEX idx_alumnos_correo ON alumnos(correo);


