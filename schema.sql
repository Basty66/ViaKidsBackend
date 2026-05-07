-- ViaKids Database Schema for Supabase/Neon
-- Execute this in your Supabase SQL Editor or Neon console

-- Enable UUID extension if using UUIDs
-- CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Users table (authentication)
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(50) NOT NULL CHECK (rol IN ('ADMIN', 'DRIVER', 'PARENT')),
    telefono VARCHAR(50),
    estado VARCHAR(50) NOT NULL DEFAULT 'ACTIVO' CHECK (estado IN ('ACTIVO', 'SUSPENDIDO')),
    extra VARCHAR(255) DEFAULT '-'
);

-- Students table
CREATE TABLE IF NOT EXISTS students (
    id VARCHAR(255) PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    curso VARCHAR(100) NOT NULL,
    rut VARCHAR(50) NOT NULL UNIQUE,
    apoderado VARCHAR(255) NOT NULL,
    telefono VARCHAR(50) NOT NULL,
    bus_id VARCHAR(255),
    bus_patente VARCHAR(50),
    ruta VARCHAR(255),
    colegio VARCHAR(255) NOT NULL,
    estado VARCHAR(50) NOT NULL DEFAULT 'EN_ESPERA' CHECK (estado IN ('EN_ESPERA', 'EN_EL_BUS', 'ENTREGADO', 'AUSENTE'))
);

-- Buses table
CREATE TABLE IF NOT EXISTS buses (
    id VARCHAR(255) PRIMARY KEY,
    patente VARCHAR(50) NOT NULL UNIQUE,
    conductor VARCHAR(255) NOT NULL,
    capacidad INTEGER NOT NULL,
    estado VARCHAR(50) NOT NULL DEFAULT 'EN_ESPERA' CHECK (estado IN ('EN_RUTA', 'EN_ESPERA')),
    tiempo_estimado VARCHAR(50) DEFAULT '--',
    lat DOUBLE PRECISION,
    lng DOUBLE PRECISION
);

-- Routes table
CREATE TABLE IF NOT EXISTS routes (
    id VARCHAR(255) PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    colegio VARCHAR(255) NOT NULL,
    bus_id VARCHAR(255),
    horario VARCHAR(50),
    paradas INTEGER DEFAULT 0
);

-- Attendance records table
CREATE TABLE IF NOT EXISTS attendance_records (
    id VARCHAR(255) PRIMARY KEY,
    student_id VARCHAR(255) NOT NULL,
    student_name VARCHAR(255),
    bus_id VARCHAR(255),
    bus_patente VARCHAR(50),
    route VARCHAR(255),
    timestamp TIMESTAMP NOT NULL,
    action VARCHAR(50) NOT NULL CHECK (action IN ('BOARDED', 'DISEMBARKED', 'ABSENT')),
    trip_type VARCHAR(50) NOT NULL CHECK (trip_type IN ('MORNING', 'AFTERNOON')),
    FOREIGN KEY (student_id) REFERENCES students(id)
);

-- Notifications table
CREATE TABLE IF NOT EXISTS notifications (
    id SERIAL PRIMARY KEY,
    fecha VARCHAR(100),
    tipo VARCHAR(50) CHECK (tipo IN ('ALERTA', 'INFO')),
    mensaje TEXT NOT NULL,
    ruta VARCHAR(255),
    leido BOOLEAN DEFAULT FALSE
);

-- Incidents table
CREATE TABLE IF NOT EXISTS incidents (
    id SERIAL PRIMARY KEY,
    fecha VARCHAR(100) NOT NULL,
    tipo VARCHAR(50) NOT NULL CHECK (tipo IN ('MECANICO', 'TRAFICO')),
    descripcion TEXT NOT NULL,
    bus VARCHAR(50) NOT NULL,
    resuelto BOOLEAN DEFAULT FALSE
);

-- Insert default admin user (password: admin123)
-- Password is BCrypt encoded
INSERT INTO users (nombre, email, password, rol, telefono, estado, extra)
VALUES ('Admin ViaKids', 'admin@viakids.cl', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LPeTukzIVCG', 'ADMIN', '+56912345678', 'ACTIVO', '-')
ON CONFLICT (email) DO NOTHING;

-- Insert sample bus
INSERT INTO buses (id, patente, conductor, capacidad, estado, tiempo_estimado, lat, lng)
VALUES ('B001', 'AB-1234', 'Juan Pérez', 40, 'EN_ESPERA', '--', -33.4489, -70.6693)
ON CONFLICT (id) DO NOTHING;

-- Insert sample route
INSERT INTO routes (id, nombre, colegio, bus_id, horario, paradas)
VALUES ('R001', 'Ruta Norte', 'Colegio Los Andes', 'B001', '07:30', 8)
ON CONFLICT (id) DO NOTHING;

-- Insert sample student
INSERT INTO students (id, nombre, curso, rut, apoderado, telefono, bus_id, bus_patente, ruta, colegio, estado)
VALUES ('S001', 'Mateo García', '4to B', '20.123.456-7', 'Carlos García', '+56912345678', 'B001', 'AB-1234', 'Ruta Norte', 'Colegio Los Andes', 'EN_ESPERA')
ON CONFLICT (id) DO NOTHING;

-- Indexes for performance
CREATE INDEX IF NOT EXISTS idx_students_bus_id ON students(bus_id);
CREATE INDEX IF NOT EXISTS idx_students_ruta ON students(ruta);
CREATE INDEX IF NOT EXISTS idx_attendance_student_id ON attendance_records(student_id);
CREATE INDEX IF NOT EXISTS idx_attendance_timestamp ON attendance_records(timestamp);
CREATE INDEX IF NOT EXISTS idx_routes_bus_id ON routes(bus_id);
