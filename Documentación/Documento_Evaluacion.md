# ViaKids - Sistema de Gestión de Transporte Escolar

## Documentación de Proyecto - Evaluación Parcial N°2
### Estado de Avance N°2 - Diseño y Desarrollo de la Solución

---

## 1. Información del Proyecto

| Campo | Detalle |
|-------|---------|
| **Nombre** | ViaKids |
| **Descripción** | Sistema web para la gestión de transporte escolar que permite a apoderados, conductores y administradores coordinar rutas, registrar asistencia y gestionar incidentes |
| **Asignatura** | TPY1101 - Taller Aplicado de Programación |
| **Docente** | Por definir |

---

## 2. Metodología de Desarrollo

### Metodología: Ágil (Scrum adaptado)

Se utiliza una metodología ágil basada en Scrum, con sprints de 2 semanas y reuniones de coordinación semanales.

**Roles del equipo:**
- **Product Owner**: Define prioridades del negocio
- **Scrum Master**: Facilita el proceso ágil
- **Development Team**: Implementa las funcionalidades

**Sprint actual:** Sprint 2 - Implementación de backend y frontend básico

**Artefactos utilizados:**
- Product Backlog (historias de usuario priorizadas)
- Sprint Backlog (tareas del sprint actual)
- Tablero Kanban (GitHub Projects)
- Reuniones diarias (Daily Standup)
- Sprint Review al final de cada iteración

---

## 3. Reglas de Negocio

### Reglas Generales

| ID | Regla de Negocio | Descripción |
|----|-------------------|-------------|
| RN01 | Gestión de usuarios | El sistema debe permitir registrar usuarios con roles: ADMIN, DRIVER (conductor), PARENT (apoderado) |
| RN02 | Autenticación | Todos los accesos al sistema requieren autenticación mediante JWT |
| RN03 | Roles y permisos | Cada rol tiene permisos específicos: ADMIN gestiona todo, DRIVER ve sus rutas, PARENT ve sus hijos |
| RN04 | Registro de estudiantes | Solo ADMIN puede registrar, modificar o eliminar estudiantes |
| RN05 | Asignación de rutas | Un estudiante puede estar asignado a un bus y una ruta |
| RN06 | Registro de asistencia | El conductor puede registrar asistencia (Presente, Ausente, Tardanza, Justificado) por estudiante y fecha |
| RN07 | Notificaciones | El sistema envía notificaciones a los apoderados cuando se registra la asistencia de sus hijos |
| RN08 | Reporte de incidentes | Cualquier usuario puede reportar un incidente asociado a un bus |
| RN09 | Trazabilidad | Todos los registros de asistencia e incidentes deben mantener fecha y hora |
| RN10 | Estado de estudiantes | Un estudiante puede estar activo o inactivo en el sistema |

### Historias de Usuario

| ID | Historia de Usuario | Prioridad | Estado |
|----|---------------------|-----------|--------|
| HU01 | Como ADMIN quiero iniciar sesión para acceder al sistema | Alta | ✅ |
| HU02 | Como ADMIN quiero gestionar estudiantes (CRUD) para mantener la información actualizada | Alta | ✅ |
| HU03 | Como ADMIN quiero gestionar buses (CRUD) para mantener la flota actualizada | Alta | ✅ |
| HU04 | Como ADMIN quiero gestionar rutas (CRUD) para asignar recorridos | Alta | ✅ |
| HU05 | Como DRIVER quiero ver mi ruta asignada para conocer mi recorrido | Media | 🚧 |
| HU06 | Como DRIVER quiero registrar la asistencia de los estudiantes en mi bus | Alta | ✅ |
| HU07 | Como PARENT quiero ver la asistencia de mis hijos para saber si llegaron al colegio | Alta | 🚧 |
| HU08 | Como PARENT quiero recibir notificaciones de la asistencia de mis hijos | Media | 🚧 |
| HU09 | Como ADMIN quiero generar reportes de asistencia para evaluar el servicio | Media | ✅ |
| HU10 | Como ADMIN quiero gestionar incidentes para mantener un registro de eventos | Alta | ✅ |

---

## 4. Arquitectura de la Solución

### Diagrama de Arquitectura (Cliente-Servidor)

```
┌─────────────────────────────────────────────────────────────┐
│                     FRONTEND (React + Vite)                  │
│  ┌─────────────┐  ┌─────────────┐  ┌───────────────────┐   │
│  │ AdminDashboard│  │ DriverDashboard│  │ ParentDashboard│   │
│  └──────┬───────┘  └──────┬───────┘  └────────┬────────┘   │
│         └──────────────────┼──────────────────┘             │
│                           │                                 │
│              API Service (axios + JWT)                      │
└───────────────────────────┼─────────────────────────────────┘
                            │ HTTP/HTTPS (JSON)
                            │ JWT Token
┌───────────────────────────┼─────────────────────────────────┐
│                   BACKEND (Django REST Framework)           │
│  ┌──────────────────────────────────────────────────────┐   │
│  │                   API REST                            │   │
│  │  /api/auth/  /api/students/  /api/buses/             │   │
│  │  /api/routes/  /api/attendance/  /api/incidents/     │   │
│  │  /api/notifications/  /api/reports/                  │   │
│  └──────────────────────┬───────────────────────────────┘   │
│                         │                                    │
│  ┌──────────────────────┴───────────────────────────────┐   │
│  │              Capa de Servicios / Lógica               │   │
│  │  JWT Authentication  │  Rol-based Access Control     │   │
│  └──────────────────────┬───────────────────────────────┘   │
│                         │                                    │
│  ┌──────────────────────┴───────────────────────────────┐   │
│  │     Django ORM / Modelos (Student, Bus, Route, ...)  │   │
│  └──────────────────────┬───────────────────────────────┘   │
└─────────────────────────┼──────────────────────────────────┘
                          │
┌─────────────────────────┴──────────────────────────────────┐
│              BASE DE DATOS (PostgreSQL - Neon)              │
│   Tablas: users, students, buses, routes,                  │
│           attendance_records, notifications, incidents      │
└────────────────────────────────────────────────────────────┘
```

### Capas del Backend

| Capa | Tecnología | Función |
|------|-----------|---------|
| **API** | Django REST Framework (DRF) | Exposición de endpoints REST |
| **Serializers** | DRF Serializers | Validación y transformación de datos |
| **Views** | DRF ViewSets y APIViews | Lógica de negocio y controladores |
| **Models** | Django ORM | Mapeo objeto-relacional |
| **Auth** | SimpleJWT + Custom User | Autenticación y autorización |

---

## 5. Tecnologías y Lenguajes Utilizados

### Stack Tecnológico

| Componente | Tecnología | Versión | Propósito |
|-----------|-----------|---------|-----------|
| **Backend** | Python + Django | 3.14 / 5.0.6 | Framework web principal |
| **API REST** | Django REST Framework | 3.15.1 | Creación de API RESTful |
| **Autenticación** | SimpleJWT | 5.3.1 | JWT Auth |
| **Base de Datos** | PostgreSQL (Neon) | 16 | Base de datos relacional |
| **CORS** | django-cors-headers | 4.3.1 | Habilitar CORS |
| **Frontend** | React + Vite | 18 / 5 | Interfaz de usuario |
| **HTTP Client** | Axios | 1.6 | Peticiones HTTP desde frontend |
| **Servidor Web** | Gunicorn | 22.0.0 | WSGI production server |
| **Despliegue** | Render (Cloud) | - | Hosting del backend |
| **Frontend Hosting** | Vercel | - | Hosting del frontend |
| **Control Versiones** | Git + GitHub | - | Repositorio del código |

### Justificación de Tecnologías

| Decisión | Justificación |
|----------|---------------|
| **Django en vez de Spring Boot** | Django tiene menor consumo de memoria, despliegue más simple en Render, desarrollo más rápido para APIs REST |
| **PostgreSQL** | Base de datos relacional robusta, soporte nativo en Django ORM, Neon ofrece capa gratuita con 500MB |
| **JWT** | Autenticación stateless, ideal para APIs REST, no requiere sesiones en servidor |
| **React + Vite** | Framework moderno, rápido desarrollo de interfaces SPA, Vite es más rápido que CRA |
| **Render** | Despliegue gratuito de Python, soporte nativo de Django, CDN global |

---

## 6. Modelo de Base de Datos

### Diagrama Entidad-Relación

```
┌──────────────────┐       ┌──────────────────┐
│      User        │       │      Bus         │
├──────────────────┤       ├──────────────────┤
│ id (PK)          │       │ id (PK)          │
│ email (UNIQUE)   │       │ bus_number (UNIQUE)│
│ password         │       │ capacity          │
│ first_name       │       │ driver_id (FK)→User│
│ last_name        │       │ status           │
│ role (ADMIN/     │       └────────┬─────────┘
│       DRIVER/    │               │
│       PARENT)    │               │1
│ active           │               │
│ created_at       │               │N
│ groups (M2M)     │               │
│ permissions (M2M)│       ┌────────┴─────────┐
└──────┬───────────┘       │      Route       │
       │1                  ├──────────────────┤
       │                   │ id (PK)          │
       │N                  │ name             │
       │                   │ description      │
       │N                  │ start_point      │
       │1                  │ end_point        │
       │                   │ bus_id (FK)→Bus  │
       │N                  └──────────────────┘
       │
┌──────┴───────────┐       ┌──────────────────┐
│     Student      │       │AttendanceRecord  │
├──────────────────┤       ├──────────────────┤
│ id (PK)          │1───N  │ id (PK)          │
│ first_name       │       │ student_id (FK)  │
│ last_name        │       │ date             │
│ date_of_birth    │       │ status (PRESENT/ │
│ grade            │       │         ABSENT/  │
│ parent_id (FK)   │       │         LATE/    │
│ bus_id (FK)→Bus  │       │         EXCUSED) │
│ active           │       │ check_in_time    │
└──────────────────┘       │ check_out_time   │
                           │ UNIQUE(student,date)
┌──────────────────┐       └──────────────────┘
│  Notification    │
├──────────────────┤       ┌──────────────────┐
│ id (PK)          │       │    Incident      │
│ user_id (FK)→User│       ├──────────────────┤
│ title            │       │ id (PK)          │
│ message          │       │ bus_id (FK)→Bus  │
│ type             │       │ reported_by→User │
│ is_read          │       │ description      │
│ created_at       │       │ severity (LOW/   │
└──────────────────┘       │          MEDIUM/ │
                           │          HIGH/    │
                           │          CRITICAL)│
                           │ status (OPEN/     │
                           │   IN_PROGRESS/    │
                           │   RESOLVED/CLOSED)│
                           │ created_at       │
                           │ resolved_at      │
                           └──────────────────┘
```

### Diccionario de Datos

#### Tabla: `api_user`
| Columna | Tipo | Restricciones | Descripción |
|---------|------|---------------|-------------|
| id | BIGINT | PK, Auto | Identificador único |
| email | VARCHAR(254) | UNIQUE, NOT NULL | Correo electrónico (username de login) |
| password | VARCHAR(128) | NOT NULL | Hash BCrypt de la contraseña |
| first_name | VARCHAR(150) | - | Nombre del usuario |
| last_name | VARCHAR(150) | - | Apellido del usuario |
| role | VARCHAR(50) | - | ADMIN, DRIVER, PARENT |
| active | BOOLEAN | default=true | Si el usuario está activo |
| date_joined | DATETIME | Auto | Fecha de creación |

#### Tabla: `api_bus`
| Columna | Tipo | Restricciones | Descripción |
|---------|------|---------------|-------------|
| id | BIGINT | PK, Auto | Identificador único |
| bus_number | VARCHAR(50) | UNIQUE, NOT NULL | Número o patente del bus |
| capacity | INTEGER | NOT NULL | Capacidad de pasajeros |
| driver_id | BIGINT | FK → api_user | Conductor asignado |
| status | VARCHAR(50) | default='ACTIVE' | Estado del bus |

#### Tabla: `api_student`
| Columna | Tipo | Restricciones | Descripción |
|---------|------|---------------|-------------|
| id | BIGINT | PK, Auto | Identificador único |
| first_name | VARCHAR(255) | NOT NULL | Nombre del estudiante |
| last_name | VARCHAR(255) | NOT NULL | Apellido del estudiante |
| date_of_birth | DATE | - | Fecha de nacimiento |
| grade | VARCHAR(50) | - | Curso/Nivel |
| parent_id | BIGINT | FK → api_user | Apoderado |
| bus_id | BIGINT | FK → api_bus | Bus asignado |
| active | BOOLEAN | default=true | Estado activo/inactivo |

#### Tabla: `api_route`
| Columna | Tipo | Restricciones | Descripción |
|---------|------|---------------|-------------|
| id | BIGINT | PK, Auto | Identificador único |
| name | VARCHAR(255) | NOT NULL | Nombre de la ruta |
| description | TEXT | - | Descripción |
| start_point | VARCHAR(255) | - | Punto de inicio |
| end_point | VARCHAR(255) | - | Punto de término |
| bus_id | BIGINT | FK → api_bus | Bus asignado |

#### Tabla: `api_attendancerecord`
| Columna | Tipo | Restricciones | Descripción |
|---------|------|---------------|-------------|
| id | BIGINT | PK, Auto | Identificador único |
| student_id | BIGINT | FK → api_student | Estudiante |
| date | DATE | NOT NULL | Fecha |
| status | VARCHAR(50) | NOT NULL | PRESENT, ABSENT, LATE, EXCUSED |
| check_in_time | TIME | - | Hora de ingreso |
| check_out_time | TIME | - | Hora de salida |
| | | UNIQUE(student, date) | Un registro por estudiante/día |

#### Tabla: `api_notification`
| Columna | Tipo | Restricciones | Descripción |
|---------|------|---------------|-------------|
| id | BIGINT | PK, Auto | Identificador único |
| user_id | BIGINT | FK → api_user | Usuario destino |
| title | VARCHAR(255) | NOT NULL | Título |
| message | TEXT | - | Mensaje |
| type | VARCHAR(50) | - | Tipo de notificación |
| is_read | BOOLEAN | default=false | Leída o no |
| created_at | DATETIME | Auto | Fecha de creación |

#### Tabla: `api_incident`
| Columna | Tipo | Restricciones | Descripción |
|---------|------|---------------|-------------|
| id | BIGINT | PK, Auto | Identificador único |
| bus_id | BIGINT | FK → api_bus | Bus relacionado |
| reported_by_id | BIGINT | FK → api_user | Quién reportó |
| description | TEXT | NOT NULL | Descripción del incidente |
| severity | VARCHAR(50) | NOT NULL | LOW, MEDIUM, HIGH, CRITICAL |
| status | VARCHAR(50) | default='OPEN' | OPEN, IN_PROGRESS, RESOLVED, CLOSED |
| created_at | DATETIME | Auto | Fecha de creación |
| resolved_at | DATETIME | - | Fecha de resolución |

---

## 7. Bosquejo de Formularios / Interfaz

### 7.1 Pantalla de Login
```
┌──────────────────────────────────────┐
│                                      │
│           🚌  ViaKids                │
│     Sistema de Transporte Escolar     │
│                                      │
│    ┌────────────────────────────┐    │
│    │  Correo electrónico        │    │
│    └────────────────────────────┘    │
│    ┌────────────────────────────┐    │
│    │  Contraseña                │    │
│    └────────────────────────────┘    │
│                                      │
│    ┌────────────────────────────┐    │
│    │       Iniciar Sesión       │    │
│    └────────────────────────────┘    │
│                                      │
│    ¿No tienes cuenta? Registrarse    │
└──────────────────────────────────────┘
```

### 7.2 Dashboard - Admin
```
┌────────────────────────────────────────────────┐
│ 🚌 ViaKids  [Admin]  [Cerrar Sesión]        │
├────────────────────────────────────────────────┤
│ ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────┐│
│ │Estudiantes│ │  Buses   │ │  Rutas   │ │Report││
│ │    15     │ │    3     │ │    5     │ │  ies ││
│ │ Activos   │ │Activos   │ │Asignadas │ │  ▶   ││
│ └──────────┘ └──────────┘ └──────────┘ └──────┘│
│                                                 │
│ Mantenedores:                                   │
│ [Estudiantes] [Buses] [Rutas] [Usuarios]        │
│ [Asistencia] [Incidentes] [Notificaciones]       │
│                                                 │
│ ┌─────────────── Asistencia Hoy ──────────────┐ │
│ │ Estudiante    | Bus | Estado  | Hora        │ │
│ │───────────────|─────|────────|──────────────│ │
│ │ Juan Pérez    | B01 | Presente| 08:15       │ │
│ │ María López   | B01 | Ausente | -           │ │
│ │ Carlos Soto   | B02 | Tardanza| 08:35       │ │
│ └─────────────────────────────────────────────┘ │
└────────────────────────────────────────────────┘
```

### 7.3 Dashboard - Driver (Conductor)
```
┌────────────────────────────────────────────────┐
│ 🚌 ViaKids  [Conductor: Carlos]  [Cerrar]   │
├────────────────────────────────────────────────┤
│ ┌──────────────────────────────────────────┐   │
│ │  🚍 Mi Bus: B01 - Capacidad: 20         │   │
│ │  📍 Ruta: Las Condes - Centro           │   │
│ └──────────────────────────────────────────┘   │
│                                                 │
│ [📷 Registrar Asistencia]     [📋 Ver Ruta]     │
│                                                 │
│ ┌── Pasajeros del Día: 12/15 ────────────────┐ │
│ │ Estudiante      |  Asistencia  | Acción    │ │
│ │─────────────────|──────────────|───────────│ │
│ │ Ana Martínez    | ✅ Presente  | ──        │ │
│ │ Pedro Rojas     | ❌ Ausente   | ──        │ │
│ │ Luis Flores     | ⏳ Pendiente | [Marcar]  │ │
│ └─────────────────────────────────────────────┘ │
└────────────────────────────────────────────────┘
```

### 7.4 Dashboard - Parent (Apoderado)
```
┌────────────────────────────────────────────────┐
│ 🚌 ViaKids  [Apoderado: María]  [Cerrar]    │
├────────────────────────────────────────────────┤
│                                                 │
│  👋 Bienvenida, María López                     │
│                                                 │
│ ┌── Mis Hijos ────────────────────────────────┐ │
│ │  👦 Juan Pérez    - Curso: 3° Básico       │ │
│ │     Bus: B01 - Ruta: Las Condes             │ │
│ │     Estado Hoy: ✅ Presente - 08:15         │ │
│ │     [Ver Historial]                         │ │
│ ├─────────────────────────────────────────────┤ │
│ │  👧 Sofía Pérez   - Curso: 5° Básico        │ │
│ │     Bus: B01 - Ruta: Las Condes             │ │
│ │     Estado Hoy: ✅ Presente - 08:16         │ │
│ │     [Ver Historial]                         │ │
│ └─────────────────────────────────────────────┘ │
│                                                 │
│ 🔔 Notificaciones (2 sin leer)                  │
│ └──────────────────────────────────────────────┘ │
└────────────────────────────────────────────────┘
```

### 7.5 Formulario: Registro de Estudiante
```
┌────────────────────────────────────────────────┐
│  ✏️ Nuevo Estudiante                           │
├────────────────────────────────────────────────┤
│                                                 │
│ ┌────────────────────────────────────────────┐  │
│ │ Nombres:  [_____________________________]  │  │
│ ├────────────────────────────────────────────┤  │
│ │ Apellidos: [_____________________________]  │  │
│ ├────────────────────────────────────────────┤  │
│ │ Fecha Nac.: [dd/mm/aaaa]                    │  │
│ ├────────────────────────────────────────────┤  │
│ │ Curso:      [3° Básico        ▼]           │  │
│ ├────────────────────────────────────────────┤  │
│ │ Apoderado:  [maria@email.com  ▼]           │  │
│ ├────────────────────────────────────────────┤  │
│ │ Bus:        [B01 - Las Condes▼]            │  │
│ ├────────────────────────────────────────────┤  │
│ │ Estado:     [● Activo]  [○ Inactivo]       │  │
│ └────────────────────────────────────────────┘  │
│                                                 │
│    [Cancelar]              [Guardar]            │
└────────────────────────────────────────────────┘
```

---

## 8. Gestión de Usuarios y Mantenedores

### Roles del Sistema

| Rol | Descripción | Permisos |
|-----|-------------|----------|
| **ADMIN** | Administrador general | CRUD completo de estudiantes, buses, rutas, usuarios, incidentes. Visualización de reportes |
| **DRIVER** | Conductor de bus | Ver ruta asignada, registrar asistencia de pasajeros, reportar incidentes |
| **PARENT** | Apoderado | Ver estado de sus hijos, ver notificaciones, ver historial de asistencia |

### Mantenedores (CRUD)

| Módulo | Admin | Driver | Parent |
|--------|-------|--------|--------|
| Estudiantes | CRUD completo | Solo lectura | Solo sus hijos |
| Buses | CRUD completo | Solo su bus | Solo lectura |
| Rutas | CRUD completo | Solo su ruta | Solo lectura |
| Usuarios | CRUD completo | No accede | No accede |
| Asistencia | Solo lectura | CRUD por fecha | Solo sus hijos |
| Incidentes | CRUD completo | Crear y ver | Solo lectura |
| Notificaciones | Enviar a todos | Leer las suyas | Leer las suyas |
| Reportes | Generar todos | - | - |

### Matriz de Acceso a Endpoints

| Endpoint | Método | ADMIN | DRIVER | PARENT |
|----------|--------|-------|--------|--------|
| `/api/auth/login/` | POST | ✅ | ✅ | ✅ |
| `/api/auth/register/` | POST | ✅ | ❌ | ✅ |
| `/api/students/` | GET | ✅ | ✅ | Solo hijos |
| `/api/students/` | POST | ✅ | ❌ | ❌ |
| `/api/students/{id}/` | PUT/DELETE | ✅ | ❌ | ❌ |
| `/api/buses/` | GET/POST | ✅ | Solo vista | ✅ |
| `/api/buses/{id}/` | PUT/DELETE | ✅ | ❌ | ❌ |
| `/api/routes/` | GET/POST | ✅ | Solo vista | ✅ |
| `/api/attendance/` | GET | ✅ | ✅ | Solo hijos |
| `/api/attendance/` | POST | ✅ | ✅ | ❌ |
| `/api/notifications/` | GET | ✅ | ✅ | ✅ |
| `/api/notifications/` | POST | ✅ | ❌ | ❌ |
| `/api/incidents/` | GET/POST | ✅ | ✅ | ✅ |
| `/api/incidents/{id}/` | PUT | ✅ | ❌ | ❌ |
| `/api/reports/` | GET | ✅ | ❌ | ❌ |

---

## 9. Configuración del Ambiente de Pruebas

### Entornos

| Entorno | URL | Base de Datos | Propósito |
|---------|-----|---------------|-----------|
| **Desarrollo Local** | `http://localhost:8000` | Neon PostgreSQL (Cloud) | Desarrollo y pruebas unitarias |
| **Producción** | `https://viakids-backend.onrender.com` | Neon PostgreSQL (Cloud) | Despliegue final |

### Variables de Entorno (entorno local)

Archivo `.env` en la raíz del proyecto:

```env
DATABASE_URL=jdbc:postgresql://ep-fancy-dust-apsbu3se-pooler.c-7.us-east-1.aws.neon.tech:6543/neondb?sslmode=require
DATABASE_USERNAME=neondb_owner
DATABASE_PASSWORD=npg_SABXo74LEZxh
JWT_SECRET=viaKidsSecretKey2026SuperSecureLongKeyForHS512SignatureAlgorithmMinimum512BitsRequired
JWT_EXPIRATION=86400000
CORS_ORIGINS=http://localhost:5173,https://via-kids-capstone.vercel.app
DJANGO_SECRET_KEY=django-insecure-viakids-dev-key
DJANGO_DEBUG=True
DJANGO_ALLOWED_HOSTS=localhost,127.0.0.1
PORT=8000
```

### Pasos para ejecutar en Local

```bash
# 1. Clonar repositorio
git clone https://github.com/Basty66/ViaKidsBackend.git
cd ViaKidsBackend

# 2. Crear entorno virtual
python -m venv venv
source venv/bin/activate  # Linux/Mac
.\venv\Scripts\activate   # Windows

# 3. Instalar dependencias
pip install -r requirements.txt

# 4. Configurar variables de entorno
# Copiar .env.example a .env y editar

# 5. Ejecutar migraciones
python manage.py migrate

# 6. Poblar datos iniciales
python manage.py seed_data

# 7. Iniciar servidor
python manage.py runserver 8000

# 8. El servidor corre en http://localhost:8000/
```

### Datos de Prueba

| Email | Contraseña | Rol |
|-------|------------|-----|
| `admin@viakids.cl` | `admin123` | ADMIN |
| `driver@viakids.cl` | `driver123` | DRIVER |
| `apoderado@viakids.cl` | `apoderado123` | PARENT |

---

## 10. Aspectos de Integración

### 10.1 Backend ↔ Base de Datos (Neon PostgreSQL)

- **Driver**: psycopg 3 (compatible con Python 3.14)
- **Conexión**: SSL requerido (`sslmode=require`)
- **ORM**: Django ORM (mapeo automático de modelos a tablas)
- **Migraciones**: Automáticas con `python manage.py migrate`

### 10.2 Frontend ↔ Backend (API REST)

- **Protocolo**: HTTP/HTTPS con JSON
- **Autenticación**: JWT (Bearer token en header `Authorization`)
- **Formato**: RESTful con endpoints estándar
- **CORS**: Configurado para permitir orígenes del frontend

### 10.3 Flujo de Autenticación JWT

```
Frontend                     Backend
   │                           │
   │  POST /api/auth/login/    │
   │  {email, password}        │
   │──────────────────────────►│
   │                           │── Verifica credenciales
   │                           │── Genera JWT (access + refresh)
   │  {token, role, email}    │
   │◄──────────────────────────│
   │                           │
   │  GET /api/students/       │
   │  Authorization: Bearer JWT│
   │──────────────────────────►│
   │                           │── Valida JWT
   │                           │── Verifica permisos
   │  {students: [...]}       │
   │◄──────────────────────────│
```

### 10.4 Configuración CORS

```python
# Permite peticiones desde:
CORS_ALLOWED_ORIGINS = [
    "http://localhost:5173",           # Frontend local (Vite)
    "https://via-kids-capstone.vercel.app",  # Frontend producción
]
```

---

## 11. Plan de Pruebas Preliminar

### Pruebas Unitarias (Backend)

| ID | Módulo | Prueba | Resultado Esperado |
|----|--------|--------|--------------------|
| PU-01 | Auth | Login con credenciales válidas | Retorna JWT token |
| PU-02 | Auth | Login con email incorrecto | 401 Unauthorized |
| PU-03 | Auth | Login con contraseña incorrecta | 401 Unauthorized |
| PU-04 | Students | Crear estudiante como ADMIN | 201 Created |
| PU-05 | Students | Crear estudiante sin autenticación | 401 Unauthorized |
| PU-06 | Students | Listar estudiantes como PARENT | Solo sus hijos |
| PU-07 | Buses | Listar buses como ADMIN | Todos los buses |
| PU-08 | Attendance | Registrar asistencia | 201 Created |
| PU-09 | Attendance | Registrar asistencia duplicada | Error de unicidad |
| PU-10 | Reports | Generar reporte de estudiante | Datos agregados |

### Pruebas de Integración

| ID | Escenario | Pasos | Resultado Esperado |
|----|-----------|-------|--------------------|
| PI-01 | Login → Listar estudiantes | 1. Login como ADMIN | Token válido + lista de estudiantes |
| PI-02 | Crear estudiante → Ver en lista | 1. CREAR estudiante | Aparece en GET /students/ |
| PI-03 | Login como PARENT | 1. Login apoderado | Ver solo sus hijos |
| PI-04 | Login como DRIVER | 1. Login conductor | Ver solo su bus/ruta |
| PI-05 | Reporte estudiante | 1. Login ADMIN | Datos de asistencia + estudiante |

### Pruebas de Carga (Pendiente)

| ID | Escenario | Métrica Esperada |
|----|-----------|------------------|
| PC-01 | 10 usuarios concurrentes | Respuesta < 2s |
| PC-02 | 100 estudiantes por bus | Listado < 3s |

---

## 12. Organización del Repositorio GitHub

```
ViaKidsBackend/
├── Documentación/              # Documentos del proyecto
│   ├── Documento_Evaluacion.md  # Este documento
│   └── Diagramas/               # (pendiente) Diagrams
│
├── Producto/                    # Código fuente
│   ├── Docs/                    # Documentación técnica
│   └── backend/                 # (código en raíz)
│       ├── api/                 # Aplicación Django
│       │   ├── models.py        # Modelos (ORM)
│       │   ├── serializers.py   # Serializers DRF
│       │   ├── views.py         # ViewSets y APIViews
│       │   ├── urls.py          # Rutas API
│       │   ├── admin.py         # Admin Django
│       │   └── management/commands/
│       │       └── seed_data.py # Poblar datos iniciales
│       ├── viakids_backend/     # Configuración Django
│       │   ├── settings.py      # Configuración
│       │   ├── urls.py          # Rutas raíz
│       │   └── wsgi.py          # WSGI
│       ├── manage.py            # Admin CLI
│       ├── requirements.txt     # Dependencias Python
│       ├── app.py               # Entry point Render
│       ├── render.yaml          # Config Render
│       └── Procfile             # Proceso web
│
├── Gestión/                    # Gestión del proyecto
│   ├── Planificación/           # (pendiente)
│   └── Actas/                   # (pendiente)
│
└── README.md                   # Descripción del proyecto
```

---

## 13. Estado Actual del Proyecto

### Funcionalidades Implementadas (Backend Django)

| Funcionalidad | Estado | Endpoint |
|---------------|--------|----------|
| Autenticación JWT (Login/Register) | ✅ Completo | `POST /api/auth/login/` |
| CRUD Estudiantes | ✅ Completo | `/api/students/` |
| CRUD Buses | ✅ Completo | `/api/buses/` |
| CRUD Rutas | ✅ Completo | `/api/routes/` |
| Registro Asistencia | ✅ Completo | `/api/attendance/` |
| Notificaciones | ✅ Completo | `/api/notifications/` |
| Incidentes | ✅ Completo | `/api/incidents/` |
| Reportes | ✅ Completo | `/api/reports/` |
| Health Check | ✅ Completo | `GET /` |
| Seed Data (3 usuarios) | ✅ Completo | `python manage.py seed_data` |

### Funcionalidades Pendientes (Frontend)

| Funcionalidad | Estado |
|---------------|--------|
| Login y Dashboard Admin | 🚧 En desarrollo |
| Dashboard Conductor | 📅 Pendiente |
| Dashboard Apoderado | 📅 Pendiente |
| Formulario CRUD Estudiantes | 📅 Pendiente |
| Registro de Asistencia (UI) | 📅 Pendiente |
| Panel de Reportes | 📅 Pendiente |
| Notificaciones en Tiempo Real | 📅 Pendiente |

### Despliegue

| Componente | Estado | URL |
|------------|--------|-----|
| Backend Django | 🚧 En deploy | `https://viakids-backend.onrender.com` |
| Base de Datos | ✅ Neon PostgreSQL | Neon Cloud |
| Frontend Vercel | 📅 Pendiente | `https://via-kids-capstone.vercel.app` |

---

## 14. Coherencia Problemática - Solución - Tecnologías

### Problemática
Los apoderados no tienen visibilidad en tiempo real del estado del transporte escolar de sus hijos. Los conductores no tienen una herramienta digital para registrar asistencia. Los administradores no pueden generar reportes eficientes.

### Solución Propuesta
Sistema web con roles diferenciados (admin, conductor, apoderado) que permite gestionar estudiantes, buses, rutas, registrar asistencia diaria, notificar automáticamente a los apoderados y generar reportes.

### Justificación Tecnológica
| Problema | Solución | Tecnología |
|----------|----------|------------|
| Visibilidad en tiempo real | API REST + Frontend SPA | Django REST + React |
| Acceso desde cualquier lugar | Web responsive | React + Vite |
| Seguridad de datos | JWT + roles | SimpleJWT + Django Permissions |
| Persistencia de datos | Base de datos relacional | PostgreSQL (Neon) |
| Despliegue escalable | Cloud hosting | Render + Vercel |
| Rapidez de desarrollo | Framework maduro | Django + DRF |

---

## 15. Enlaces y Referencias

| Recurso | URL |
|---------|-----|
| **Repositorio Backend** | [https://github.com/Basty66/ViaKidsBackend](https://github.com/Basty66/ViaKidsBackend) |
| **Frontend (Vercel)** | [https://via-kids-capstone.vercel.app](https://via-kids-capstone.vercel.app) |
| **Neon Console** | [https://console.neon.tech](https://console.neon.tech) |
| **Render Dashboard** | [https://dashboard.render.com](https://dashboard.render.com) |
| **Backend en Render** | `https://viakids-backend.onrender.com` |

---

*Documento generado para la Evaluación Parcial N°2 - TPY1101 Taller Aplicado de Programación*
*Fecha: Mayo 2026*
