# ViaKids Backend - Spring Boot

Backend para el sistema de transporte escolar ViaKids. Conecta con el frontend en React/Vite.

## 🚀 Configuración Rápida

### 1. Base de Datos (Supabase o Neon)

**Opción A: Supabase**
1. Crea proyecto en https://supabase.com
2. Ve a Project Settings > Database
3. Copia la connection string y actualiza `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://aws-0-us-west-1.pooler.supabase.com:6543/postgres?sslmode=require
spring.datasource.username=postgres.tu-ref
spring.datasource.password=tu-password
```

**Opción B: Neon**
1. Crea proyecto en https://neon.tech
2. Copia la connection string desde Dashboard
3. Actualiza `application.properties`

### 2. Ejecutar el Schema SQL
En la consola SQL de Supabase/Neon, ejecuta el archivo `schema.sql` incluido en este proyecto.

### 3. Configurar el Backend
Edita `src/main/resources/application.properties`:
```properties
# Cambia estos valores por los de tu base de datos
spring.datasource.username=postgres.tu-ref
spring.datasource.password=tu-password-real

# JWT Secret (cámbialo en producción)
jwt.secret=viaKidsSecretKey2026SuperSecureLongKeyForHS512SignatureAlgorithmMinimum512BitsRequired
```

### 4. Ejecutar la Aplicación
```bash
cd C:\Users\crist\Desktop\viakids-backend
mvn spring-boot:run
```

El backend estará en: `http://localhost:3000`

## 📋 Endpoints de la API

### Auth
- `POST /api/auth/login` - Login con `{email, password}`
- `POST /api/auth/register` - Registro de usuario
- `GET /api/auth/me` - Perfil del usuario autenticado

### Usuarios
- `GET /api/users` - Listar usuarios
- `POST /api/users` - Crear usuario
- `PUT /api/users/{id}` - Actualizar usuario
- `DELETE /api/users/{id}` - Eliminar usuario

### Estudiantes
- `GET /api/students` - Listar estudiantes
- `POST /api/students` - Crear estudiante
- `PUT /api/students/{id}` - Actualizar estudiante
- `DELETE /api/students/{id}` - Eliminar estudiante
- `GET /api/students/{id}/qr` - Obtener QR (RUT)

### Buses
- `GET /api/buses` - Listar buses
- `POST /api/buses` - Crear bus
- `PUT /api/buses/{id}` - Actualizar bus
- `DELETE /api/buses/{id}` - Eliminar bus
- `PUT /api/buses/{id}/location` - Actualizar ubicación

### Rutas
- `GET /api/routes` - Listar rutas
- `POST /api/routes` - Crear ruta
- `PUT /api/routes/{id}` - Actualizar ruta
- `DELETE /api/routes/{id}` - Eliminar ruta

### Asistencia
- `POST /api/attendance/scan?qrData=&action=&tripType=` - Escanear QR
- `GET /api/attendance?fecha=&busId=` - Obtener registros
- `GET /api/attendance/student/{studentId}` - Asistencia por estudiante

### Reportes
- `GET /api/reports/attendance?fecha=&busId=&ruta=` - Reporte de asistencia

### Notificaciones
- `GET /api/notifications` - Listar notificaciones
- `POST /api/notifications` - Crear notificación
- `PUT /api/notifications/{id}/read` - Marcar como leída

### Incidentes
- `GET /api/incidents` - Listar incidentes
- `POST /api/incidents` - Crear incidente
- `PUT /api/incidents/{id}/resolve` - Resolver incidente

## 🔐 Autenticación

El frontend envía el token en el header:
```
Authorization: Bearer <token>
```

Roles soportados:
- `ADMIN` (Administrador)
- `DRIVER` (Conductor)
- `PARENT` (Apoderado)

## 🌐 CORS

Orígenes permitidos:
- `http://localhost:5173` (desarrollo local)
- `https://via-kids-capstone.vercel.app` (producción)

## 📦 Estructura del Proyecto

```
src/main/java/com/viakids/
├── controller/       # API endpoints
├── model/           # Entidades JPA
│   └── dto/        # Data Transfer Objects
├── repository/      # Repositorios JPA
├── service/         # Lógica de negocio
├── security/        # JWT y Spring Security
├── config/          # Configuraciones (CORS, WebSocket)
└── ViaKidsApplication.java
```

## 🛠️ Tecnologías

- Java 17
- Spring Boot 3.2.5
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL (Supabase/Neon)
- WebSocket (STOMP)
- Lombok
- BCrypt para passwords

## 👤 Usuario por Defecto

Después de ejecutar el schema SQL:
- **Email:** admin@viakids.cl
- **Password:** admin123
- **Rol:** ADMIN

## 📝 Notas

- Los IDs de Student, Bus, Route y AttendanceRecord usan Strings (S001, B001, R001, A001)
- El frontend espera respuestas en español para estados y roles
- La ubicación de buses usa coordenadas Double (lat, lng)
- El WebSocket está configurado en `/ws` con SockJS
