# ViaKids - Defensa Capstone: Guía Completa
## Stack: Spring Boot 4 + Java 21 + React 19 + Neon PostgreSQL + WebSocket + QR + GPS

---

# 1. ARQUITECTURA

## "Explique la arquitectura de su solución"

```
[React 19 + Vite 8 + Tailwind 4 + Lucide]   ← Frontend (Vercel)
        ↕ HTTPS + JWT + STOMP WebSocket
[Spring Boot 4.0.6 + Java 21]               ← Backend (Railway)
        ↕ JDBC + JPA/Hibernate
[Neon PostgreSQL]                             ← Base de Datos
```

**3 capas + tiempo real:**
- **Frontend**: React 19 con Vite 8. Dashboards por rol (Admin, Driver, Parent). Lector QR con cámara. Mapas Leaflet para tracking GPS.
- **Backend**: Spring Boot 4.0.6 monolítico con paquetes por feature (auth/, student/, bus/, attendance/, etc). JWT stateless. WebSocket STOMP.
- **BD**: PostgreSQL en Neon Cloud. UUID como PK. JPA/Hibernate con ddl-auto=update.
- **Tiempo real**: STOMP sobre WebSocket. SimpleBroker por defecto, RabbitMQ opcional para producción.

## "¿Qué patrón arquitectónico usan?"

**Monolítico con separación por features** (no microservicios). Cada entidad vive en su propio paquete:
```
auth/ → login, register, JWT
student/ → CRUD estudiantes
bus/ → CRUD buses + GPS tracking
attendance/ → asistencia QR
notification/ → notificaciones con presets
incident/ → reporte de incidentes
websocket/ → controladores STOMP
config/ → Security, CORS, WebSocket, JWT, RabbitMQ
```

Para el volumen de datos de un colegio, un monolito es más simple de desarrollar, desplegar y mantener que microservicios.

## "¿Cómo fluye la información?"

```
Usuario → React → Axios (con JWT) → Spring Boot Controller
    → Service → Repository (JPA) → PostgreSQL
    → Respuesta JSON → React renderiza

Tiempo real:
Driver escanea QR → Backend guarda asistencia
    → STOMP publish a /topic/notifications
    → React suscrito recibe y muestra notificación al apoderado
```

---

# 2. FRONTEND

## React 19.2.5

### "¿Qué es React?"
Biblioteca JS (Meta) para UI con componentes reutilizables. Virtual DOM para rendimiento.

### "¿Por qué React 19?"
Última versión estable con Actions (formularios nativos), hook `use()` para promesas, y mejora en Server Components. React es el framework frontend más usado, máxima comunidad y empleabilidad.

### "¿React contra qué?"
- **Angular**: más complejo (TypeScript obligatorio, RxJS, módulos). Overhead grande para este proyecto.
- **Vue**: más simple pero menos ecosistema y menos empleabilidad.
- **React**: equilibrio perfecto.

### "¿Qué librerías usan con React?"
| Librería | Para qué |
|----------|----------|
| **react-router-dom 7** | Rutas protegidas por rol (/admin, /driver, /parent) |
| **react-hook-form + zod** | Formularios con validación |
| **axios** | Peticiones HTTP con interceptor JWT |
| **@stomp/stompjs** | WebSocket cliente para notificaciones push |
| **recharts** | Gráficos en dashboards |
| **leaflet + react-leaflet** | Mapas para tracking GPS del bus |
| **html5-qrcode** | Lector QR con cámara del navegador |
| **react-qr-code** | Generador de QR para imprimir |
| **lucide-react** | Iconos SVG |
| **xlsx** | Exportar reportes a Excel |
| **uuid** | Generar IDs únicos |

## Vite 8.0.10

### "¿Qué es Vite?"
Build tool moderna (Evan You, creador de Vue). Usa ESBuild (Go) para dev y Rollup para producción.

### "¿Por qué Vite 8?"
HMR instantáneo, builds 10x más rápidos que Webpack/CRA. Configuración cero con React. CRA está deprecated.

### "¿Qué es el proxy de Vite?"
En desarrollo, Vite proxy `/api` a `localhost:8081` para evitar CORS. En producción, lo maneja el dominio del backend.

## Tailwind CSS 4.2.4

### "¿Qué es Tailwind?"
Framework CSS utility-first: `flex items-center p-4 bg-blue-500` en vez de CSS personalizado.

### "¿Por qué Tailwind 4?"
- **Prototipado rápido**: no pensar nombres de clases CSS
- **PurgeCSS automático**: solo incluye clases usadas, CSS final mínimo
- **Responsive nativo**: `md:flex lg:grid-cols-3`
- **Modo oscuro**: `dark:bg-gray-800`
- **Alternativas**: Bootstrap (diseño genérico), Styled Components (runtime overhead), CSS Modules (más archivos)

### "¿Qué es @tailwindcss/postcss?"
Es el plugin PostCSS para Tailwind 4. Reemplaza la CLI de Tailwind, procesa los estilos durante el build de Vite.

## Lucide-react 1.14

### "¿Qué es Lucide?"
+1000 iconos open-source en SVG. Tree-shakeable (solo pesan los que usas). Diseño consistente.

## html5-qrcode 2.3.8

### "¿Qué es?"
Lee códigos QR usando la cámara del navegador (getUserMedia API). Sin apps nativas.

### "¿Para qué lo usan?"
El conductor escanea el QR del estudiante al abordar/descender. Registra asistencia automática.

## react-qr-code 2.0.21

### "¿Qué es?"
Genera códigos QR como componente React. Se usa en el panel admin para imprimir QR de cada estudiante.

---

# 3. BACKEND

## Spring Boot 4.0.6

### "¿Qué es Spring Boot?"
Framework Java que extiende Spring con auto-configuración y servidor embebido (Tomcat). Reduce drásticamente la configuración manual.

### "¿Por qué Spring Boot 4?"
Última versión con soporte para Java 21, Virtual Threads, Spring Security 7, GraalVM native images.

### "¿Por qué Spring Boot y no otro?"
- **vs Django/Python**: Java es tipado (menos errores runtime), mejor rendimiento, estándar enterprise
- **vs Node/Express**: Java/JVM tiene madurez, herramientas de monitoreo, seguridad integrada
- **vs Quarkus/Micronaut**: Spring Boot es el más usado, mayor ecosistema, más fácil encontrar recursos

### "¿Qué dependencias Spring Boot usan?"
| Dependencia | Para qué |
|-------------|----------|
| **spring-boot-starter-data-jpa** | ORM con Hibernate, repositorios automáticos |
| **spring-boot-starter-security** | Autenticación, autorización, CORS |
| **spring-boot-starter-validation** | Validación con anotaciones (@NotBlank, @Email) |
| **spring-boot-starter-webmvc** | Controladores REST, Tomcat embebido |
| **spring-boot-starter-websocket** | STOMP WebSocket para tiempo real |
| **spring-boot-starter-reactor-netty** | Servidor reactivo para WebSocket |
| **spring-boot-starter-amqp** | RabbitMQ (condicional, deshabilitado por defecto) |
| **spring-boot-devtools** | Hot reload en desarrollo |

### "¿Qué capas tiene su backend?"

```
Controller (REST) → recibe peticiones HTTP, delega a Service
    ↓
Service → lógica de negocio, orquesta repositorios
    ↓
Repository (JPA) → acceso a BD, consultas automáticas
    ↓
Entity → mapeo de tablas a objetos Java (JPA)
    ↓
PostgreSQL → base de datos
```

**Además:**
- **Security Config** → JWT filter chain, CORS, endpoints públicos/protegidos
- **WebSocket Config** → STOMP endpoints, SimpleBroker o RabbitMQ
- **DataInitializer** → seed data al iniciar (si BD vacía)

### "¿Qué son los DTOs?"
Objetos que definen cómo se reciben/envían datos en la API. Separamos la Entity (modelo BD) del DTO (lo que ve el frontend). Así no exponemos datos sensibles ni acoplamos la API a la BD.

### "¿Qué es Lombok?"
Biblioteca que genera boilerplate (getters, setters, constructores) con anotaciones: `@Data`, `@Builder`, `@NoArgsConstructor`. Menos código, más legible.

### "¿Qué es el DataInitializer?"
Clase que implementa `ApplicationListener<ApplicationReadyEvent>`. Al iniciar, si la tabla users está vacía, crea datos de prueba (3 usuarios, 3 buses, 2 rutas, 3 estudiantes, notificaciones, incidentes). Así el sistema no arranca vacío.

## Java 21

### "¿Por qué Java 21?"
LTS (Long Term Support) más reciente. Características clave:
- **Virtual Threads** (Project Loom): hilos ultra-livianos a nivel JVM. Manejan miles de conexiones concurrentes sin bloquear el SO
- **Pattern Matching**: switch expressions más potentes
- **Record Patterns**: destructuración de records
- **Sequenced Collections**: interfaces para colecciones ordenadas

## Maven

### "¿Qué es Maven?"
Herramienta de build y gestión de dependencias para Java. Usa archivo `pom.xml` para declarar dependencias, plugins y configuración.

### "¿Qué es el wrapper (mvnw)?"
`mvnw` (Maven Wrapper) permite build sin tener Maven instalado globalmente. Descarga la versión correcta automáticamente.

---

# 4. SEGURIDAD Y AUTENTICACIÓN

## JWT con JJWT 0.12.5

### "¿Qué es JWT?"
JSON Web Token (RFC 7519). Token firmado digitalmente que contiene información del usuario. Stateless: el backend no guarda sesiones, solo valida la firma.

### "¿Qué contiene el JWT?"
```
Header: { "alg": "HS512" }
Payload: { "sub": "admin@viakids.cl", "role": "ADMIN", "iat": ..., "exp": ... }
Signature: HMAC-SHA512(base64(header) + "." + base64(payload), secretKey)
```

### "¿Cómo fluye el JWT?"
1. POST /api/auth/login con email + password
2. Spring Security valida contra BD (BCrypt)
3. JJWT genera token firmado (expira 24h)
4. Frontend guarda en localStorage como `viakids_token_v3`
5. Cada petición: Axios interceptor agrega `Authorization: Bearer <token>`
6. JwtAuthenticationFilter valida token en cada request protegido
7. Si 401 → interceptor de respuesta limpia storage y redirige a login

### "¿Por qué JJWT 0.12.5?"
Biblioteca JWT más usada en Java. API fluida, segura (HMAC/RSA), mantenida activamente.

### "¿Cómo se almacena la clave secreta?"
En variable de entorno `JWT_SECRET`. Nunca en código. Spring la lee con `@Value("${app.jwt.secret}")`.

## Spring Security

### "¿Qué configura Spring Security?"
- **SecurityFilterChain**: define qué rutas son públicas (/auth/**) y cuáles protegidas
- **JwtAuthenticationFilter**: filtro personalizado que extrae JWT del header, valida firma, carga SecurityContext
- **CorsConfigurationSource**: orígenes permitidos desde variables de entorno
- **BCryptPasswordEncoder**: hash de passwords

---

# 5. BASE DE DATOS

## PostgreSQL + Neon

### "¿Por qué PostgreSQL?"
BD relacional open-source más avanzada: ACID, constraints, JSONB, extensiones. Estándar en producción.

### "¿Qué es Neon?"
Plataforma serverless PostgreSQL. Capa gratuita con 500MB, SSL obligatorio, pooler (puerto 6543), auto-pause, console web.

### "¿Por qué Neon y no otro?"
- **vs Render PostgreSQL**: Neon es serverless-first, auto-pause ahorra recursos
- **vs Railway PostgreSQL**: Neon tiene mejor UI y capa gratuita más generosa
- **vs AWS RDS**: sobredimensionado para proyecto académico

### "¿Qué modelo de BD tienen?"

**7 tablas:**

| Tabla | PK | Función |
|-------|-----|---------|
| **users** | UUID | Cuentas (ADMIN, DRIVER, PARENT) |
| **buses** | UUID | Flota con ubicación GPS y estado |
| **routes** | UUID | Rutas programadas por bus |
| **students** | UUID | Estudiantes con apoderado |
| **attendance** | UUID | Registro QR de abordaje/descenso |
| **notifications** | UUID | Mensajes con presets |
| **incidents** | UUID | Reportes de incidencias |

### "¿Por qué UUID y no auto-increment?"
Seguridad (no expones cuántos registros tienes), distribuible (no conflicto en merges), aunque ocupa más espacio en índices (irrelevante para este volumen).

### "¿Cómo manejan la conexión?"
URL JDBC desde variable `SPRING_DATASOURCE_URL`. Si no está configurada (local), cae a H2 en memoria para pruebas. Railway expone variables PGUSER, PGPASSWORD, PGHOST.

### "¿Qué es H2?"
BD en memoria para desarrollo local. Cuando no hay PostgreSQL disponible, Spring Boot usa H2 con `spring.datasource.url=jdbc:h2:mem:viakids`. Así se puede desarrollar sin depender de Neon.

### "¿Qué es JPA/Hibernate?"
**JPA** (Java Persistence API): especificación estándar para ORM en Java.
**Hibernate**: implementación de JPA más usada.
**ddl-auto=update**: Hibernate crea/actualiza tablas automáticamente según las Entities.

---

# 6. COMUNICACIÓN EN TIEMPO REAL

## STOMP sobre WebSocket

### "¿Qué es WebSocket?"
Protocolo bidireccional persistente. El servidor puede enviar datos sin que el cliente los solicite (a diferencia de HTTP request-response).

### "¿Qué es STOMP?"
Protocolo de mensajería sobre WebSocket. Proporciona destinos lógicos (`/topic/notify`, `/queue/notifications`) y suscripciones pub/sub.

### "¿Para qué lo usan?"
- **Conductor escanea QR** → backend publica en `/topic/notify` → apoderado recibe notificación al instante
- **Incidente reportado** → admin recibe alerta en dashboard en tiempo real
- **Presets de notificaciones** → conductor envía "Retraso" o "Llegada" con un clic

### "¿Qué es SimpleBroker?"
Broker de mensajes embebido en Spring. Maneja suscripciones en memoria. Suficiente para desarrollo. Destinos:
- `/topic/*` → broadcast a todos los suscriptores
- `/queue/*` → punto a punto

### "¿Qué es RabbitMQ?"
Message broker externo. Persiste mensajes en disco, permite routing complejo, escala horizontalmente. Por ahora deshabilitado (`app.rabbitmq.enabled=false`). Si el proyecto crece a múltiples instancias, se activa el `StompBrokerRelay` y RabbitMQ centraliza los mensajes.

### "¿Qué usa el frontend para WebSocket?"
**@stomp/stompjs 7.3.0**: biblioteca STOMP para JavaScript. El hook `useWebSocket` maneja la conexión, reconexión automática y suscripciones.

---

# 7. TRACKING GPS

## Leaflet + react-leaflet

### "¿Qué usan para mapas?"
**Leaflet 1.9.4**: biblioteca JS de mapas open-source. **react-leaflet 5.0.0**: wrappers React para Leaflet.

### "¿Cómo funciona el tracking?"
- Conductor inicia ruta, el frontend activa Geolocation API
- Cada 5-10s envía coordenadas PUT /buses/:id con {lat, lng}
- Apoderado ve el bus en un mapa Leaflet con polling cada 10s a GET /buses/:id
- Se usa un mock en frontend para pruebas (simula movimiento del bus)

---

# 8. ASISTENCIA QR

## "¿Cómo funciona el flujo de asistencia?"

1. Conductor inicia ruta en la app
2. Escanea QR del estudiante (cámara o archivo)
3. Frontend envía POST /attendance/scan con qrData y action (BOARDED/DISEMBARKED)
4. Backend guarda registro en attendance y actualiza estado del estudiante
5. Backend publica evento STOMP a /queue/notifications
6. Apoderado recibe notificación: "Mateo abordó el bus"

---

# 9. NOTIFICACIONES

## "¿Qué son los presets?"
Mensajes predefinidos que el conductor envía con un clic: "Retraso", "Llegada", "Emergencia", "Desvío". Cada preset tiene un targetRoles (PARENT, ADMIN) y un mensaje template. El conductor no escribe, solo selecciona.

## "¿Cómo se guardan?"
Tabla `notifications` con preset_key, preset_label, target_roles (CSV: "PARENT,ADMIN"). La data inicial se carga con seed.

---

# 10. DESPLIEGUE

## "¿Dónde despliegan?"

| Componente | Plataforma | Motivo |
|------------|-----------|--------|
| Frontend | **Vercel** | Gratuito, CDN, HTTPS auto |
| Backend | **Railway** | Gratuito para Java, CI/CD desde GitHub |
| Base de Datos | **Neon** | PostgreSQL serverless gratis |

## "¿Qué variables de entorno usan?"
- `JWT_SECRET` → clave para firmar tokens
- `SPRING_DATASOURCE_URL` → URL JDBC de Neon
- `CORS_ORIGINS` → orígenes permitidos (Vercel, localhost)
- `PGUSER`, `PGPASSWORD`, `PGHOST` → Railway PostgreSQL components

## Railway
Railway despliega Spring Boot desde GitHub. Expone variables PG* automáticamente si usas su plugin PostgreSQL. La URL JDBC se construye a partir de los componentes individuales porque Railway entrega DATABASE_URL en formato postgresql:// que Spring no acepta.

---

# 11. WORKFLOW COMPLETO

## "¿Cuál es el workflow del sistema?"

```
1. USUARIO INICIA SESIÓN
   → LoginPage.jsx → POST /auth/login → JWT → localStorage

2. SEGÚN ROL, VE SU DASHBOARD
   → Admin: AdminDashboard.jsx (CRUD completo, reportes)
   → Driver: DriverDashboard.jsx (ruta, QR scanner, incidencias)
   → Parent: ParentDashboard.jsx (hijos, tracking, notificaciones)

3. CONDUCTOR INICIA RUTA
   → Activa GPS (mock) → envía coordenadas cada 5-10s
   → Escanea QR de cada estudiante al abordar
   → POST /attendance/scan → guarda registro
   → STOMP notifica al apoderado

4. APODERADO VE EN TIEMPO REAL
   → Mapa Leaflet con posición del bus
   → Notificaciones push vía STOMP
   → Estado de sus hijos (abordo/no abordo)

5. ADMIN GESTIONA
   → CRUD de usuarios, buses, rutas, estudiantes
   → Visualiza todos los buses en tiempo real
   → Exporta reportes a Excel
   → Gestiona incidencias reportadas
```

---

# 12. TABLA RESUMEN

| Capa | Tecnología | Versión | Rol |
|------|-----------|---------|-----|
| **Frontend** | React | 19.2.5 | UI component-based |
| **Build** | Vite | 8.0.10 | Dev server + build |
| **CSS** | Tailwind CSS | 4.2.4 | Estilos utility-first |
| **Iconos** | Lucide | 1.14.0 | Iconos SVG |
| **Formularios** | react-hook-form + zod | 7.75 / 4.4 | Validación |
| **HTTP** | Axios | 1.15.2 | Peticiones con interceptor JWT |
| **WebSocket** | @stomp/stompjs | 7.3.0 | Tiempo real |
| **Mapas** | Leaflet + react-leaflet | 1.9 / 5.0 | GPS tracking |
| **QR scan** | html5-qrcode | 2.3.8 | Leer QR con cámara |
| **QR gen** | react-qr-code | 2.0.21 | Generar QR |
| **Gráficos** | Recharts | 3.8.1 | Dashboards |
| **Excel** | xlsx | 0.18.5 | Exportar reportes |
| **Backend** | Spring Boot | 4.0.6 | Framework web Java |
| **Lenguaje** | Java | 21 | LTS, Virtual Threads |
| **Build** | Maven | - | Gestión dependencias |
| **ORM** | JPA/Hibernate | - | Mapeo BD ↔ objetos |
| **Auth** | JJWT | 0.12.5 | JWT tokens |
| **Seguridad** | Spring Security | - | Filtros, BCrypt, CORS |
| **WS Backend** | Spring WebSocket | - | STOMP broker |
| **Mensajería** | RabbitMQ | condicional | Broker externo |
| **BD** | PostgreSQL | 16 | Relacional |
| **Cloud DB** | Neon | - | Serverless PostgreSQL |
| **Driver** | PostgreSQL JDBC | - | Conexión Java ↔ PG |
| **Deploy BE** | Railway | - | Cloud hosting |
| **Deploy FE** | Vercel | - | Frontend hosting |

---

# 13. FRASES CLAVE PARA LA DEFENSA

- **"Arquitectura monolítica con paquetes por feature, no microservicios porque para este volumen es más simple y eficiente"**
- **"JWT stateless con JJWT 0.12.5, el frontend guarda el token en localStorage, el backend solo valida la firma"**
- **"Spring Boot 4 con Java 21 para Virtual Threads: hilos ultra-livianos que permiten manejar miles de conexiones concurrentes"**
- **"UUID como PK en vez de auto-increment por seguridad y para evitar conflictos en distribuidos"**
- **"STOMP sobre WebSocket con SimpleBroker para notificaciones en tiempo real. RabbitMQ es condicional si escalamos a múltiples instancias"**
- **"QR con la cámara del navegador vía html5-qrcode, sin apps nativas. Cada estudiante tiene un QR único para registrar asistencia"**
- **"Leaflet + react-leaflet para el mapa de tracking GPS del bus en tiempo real"**
- **"Tailwind CSS 4 + utility-first para prototipado rápido. Con PurgeCSS, el CSS final es mínimo"**
- **"Vite 8 con HMR instantáneo, build 10x más rápido que CRA o Webpack"**
- **"Railway despliega Spring Boot desde GitHub con CI/CD automático. Si no hay PostgreSQL, cae a H2 en memoria para desarrollo local"**
- **"react-hook-form + zod para formularios con validación del lado del cliente y del servidor"**
- **"Presets de notificaciones: el conductor no escribe mensajes, selecciona plantillas predefinidas como 'Retraso' o 'Llegada'"**
