-- Crear base de datos (si no existe)
CREATE DATABASE IF NOT EXISTS instituto_db;

-- Conectar a la base de datos
\c instituto_db;

-- Crear tabla de alumnos (ya existente)
CREATE TABLE IF NOT EXISTS alumnos (
    id_alumno SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    correo VARCHAR(255) UNIQUE NOT NULL,
    materia VARCHAR(100) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Crear tabla de materias (NUEVA)
CREATE TABLE materias (
    id_materia SERIAL PRIMARY KEY,
    nombre VARCHAR(100) UNIQUE NOT NULL,
    descripcion VARCHAR(500),
    creditos INTEGER,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Crear índices para alumnos
CREATE INDEX IF NOT EXISTS idx_alumnos_correo ON alumnos(correo);
CREATE INDEX IF NOT EXISTS idx_alumnos_materia ON alumnos(materia);
CREATE INDEX IF NOT EXISTS idx_alumnos_nombre ON alumnos(nombre, apellido);

-- Crear índices para materias
CREATE INDEX idx_materias_nombre ON materias(nombre);
CREATE INDEX idx_materias_creditos ON materias(creditos);

-- Trigger para actualizar fecha_actualizacion en alumnos
CREATE OR REPLACE FUNCTION update_fecha_actualizacion()
RETURNS TRIGGER AS $$
BEGIN
    NEW.fecha_actualizacion = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger para alumnos
DROP TRIGGER IF EXISTS trigger_update_fecha_actualizacion ON alumnos;
CREATE TRIGGER trigger_update_fecha_actualizacion
    BEFORE UPDATE ON alumnos
    FOR EACH ROW
    EXECUTE FUNCTION update_fecha_actualizacion();

-- Trigger para materias
CREATE TRIGGER trigger_update_fecha_actualizacion_materias
    BEFORE UPDATE ON materias
    FOR EACH ROW
    EXECUTE FUNCTION update_fecha_actualizacion();

-- Insertar materias predeterminadas
INSERT INTO materias (nombre, descripcion, creditos) VALUES
('Matemáticas', 'Curso fundamental de matemáticas aplicadas', 4),
('Física', 'Principios básicos de física y mecánica', 4),
('Química', 'Introducción a la química general e inorgánica', 3),
('Historia', 'Historia universal y nacional', 3),
('Literatura', 'Literatura clásica y contemporánea', 3),
('Inglés', 'Inglés como segundo idioma - nivel intermedio', 3),
('Programación', 'Fundamentos de programación y desarrollo', 4),
('Biología', 'Biología celular y molecular', 4),
('Geografía', 'Geografía física y humana', 2),
('Arte', 'Apreciación artística y técnicas básicas', 2),
('Economía', 'Principios básicos de economía', 3),
('Filosofía', 'Introducción al pensamiento filosófico', 2)
ON CONFLICT (nombre) DO NOTHING;

-- Insertar datos de ejemplo para alumnos (si no existen)
INSERT INTO alumnos (nombre, apellido, correo, materia) VALUES
('Juan', 'Pérez', 'juan.perez@email.com', 'Matemáticas'),
('María', 'González', 'maria.gonzalez@email.com', 'Física'),
('Carlos', 'Rodríguez', 'carlos.rodriguez@email.com', 'Química'),
('Ana', 'López', 'ana.lopez@email.com', 'Historia'),
('Luis', 'Martínez', 'luis.martinez@email.com', 'Literatura'),
('Sofia', 'Hernández', 'sofia.hernandez@email.com', 'Inglés'),
('Diego', 'Torres', 'diego.torres@email.com', 'Programación'),
('Isabella', 'Morales', 'isabella.morales@email.com', 'Biología')
ON CONFLICT (correo) DO NOTHING;

-- Consultas útiles
-- Ver todas las materias con estadísticas
SELECT 
    m.nombre as materia,
    m.descripcion,
    m.creditos,
    COUNT(a.id_alumno) as total_alumnos
FROM materias m
LEFT JOIN alumnos a ON m.nombre = a.materia
GROUP BY m.id_materia, m.nombre, m.descripcion, m.creditos
ORDER BY total_alumnos DESC, m.nombre;

-- Ver alumnos con sus materias
SELECT 
    a.id_alumno,
    a.nombre,
    a.apellido,
    a.correo,
    a.materia,
    m.descripcion as descripcion_materia,
    m.creditos
FROM alumnos a
LEFT JOIN materias m ON a.materia = m.nombre
ORDER BY a.apellido, a.nombre;