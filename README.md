# Student Service - Arquitectura Hexagonal

## Índice

1.  [Descripción](#descripción)
2.  [Arquitectura Hexagonal](#arquitectura-hexagonal)
3.  [Tecnologías y Dependencias](#tecnologías-y-dependencias)
4.  [Base de Datos](#base-de-datos)
5.  [Seguridad y Autenticación](#seguridad-y-autenticación)
6.  [Instalación y Configuración](#instalación-y-configuración)
7.  [API Endpoints](#api-endpoints)
8.  [Testing](#testing)
9.  [Beneficios de la Arquitectura Hexagonal](#beneficios-de-la-arquitectura-hexagonal)
10. [Estructura del Proyecto](#estructura-del-proyecto)
11. [Autor](#autor)

## Descripción

Este proyecto es un **estudio práctico de la Arquitectura Hexagonal** (también conocida como Ports and Adapters), implementado como un microservicio de gestión de estudiantes con **sistema de seguridad JWT completo**. El objetivo principal es demostrar cómo esta arquitectura permite el **encapsulamiento de datos entre capas** y proporciona una separación clara de responsabilidades.

La Arquitectura Hexagonal nos permite crear aplicaciones que son **independientes de frameworks externos**, facilitando el intercambio de adaptadores sin afectar la lógica de negocio central. La implementación incluye **autenticación, autorización y gestión de usuarios** siguiendo los mismos principios arquitectónicos.

## Arquitectura Hexagonal

### ¿Qué es la Arquitectura Hexagonal?

La Arquitectura Hexagonal, propuesta por Alistair Cockburn, organiza el código en tres áreas principales:

```
┌─────────────────────────────────────────────────────────┐
│                    INFRASTRUCTURE                       │
│  ┌─────────────┐                       ┌─────────────┐  │
│  │   INPUT     │                       │   OUTPUT    │  │
│  │  ADAPTERS   │                       │  ADAPTERS   │  │
│  │             │                       │             │  │
│  │ REST API    │    ┌─────────────┐    │ DATABASE    │  │
│  │ SECURITY    │────│ APPLICATION │────│ JWT TOKEN   │  │
│  │ AUTH        │    │   (PORTS)   │    │ STORAGE     │  │
│  │             │    └─────────────┘    │ EMAIL       │  │
│  └─────────────┘                       └─────────────┘  │
└─────────────────────────────────────────────────────────┘
```

### Capas del Proyecto

#### **Domain (Núcleo)**
- **Entities**: Modelos de negocio puros (Student, User, Role, Permission)
- **Value Objects**: Objetos inmutables (AuthToken)
- **Exceptions**: Excepciones específicas del dominio (StudentNotFoundException, UserNotFoundException)

#### **Application (Puertos)**
- **Ports**: Interfaces que definen contratos
  - **Input Ports**: Casos de uso (StudentServicePort, AuthenticationServicePort, UserServicePort)
  - **Output Ports**: Repositorios y servicios externos (StudentPersistencePort, UserPersistencePort, JwtTokenPort)
- **Services**: Implementación de la lógica de negocio

#### **Infrastructure (Adaptadores)**
- **Input Adapters**: Controladores REST, filtros de seguridad, configuración JWT
- **Output Adapters**: Repositorios JPA, adaptadores de tokens, encriptación

## Tecnologías y Dependencias

### Framework Principal
- **Spring Boot 3.5.4** - Framework principal de Java
- **Java 21** - Versión LTS de Java

### Seguridad
- **Spring Security** - Framework de seguridad para autenticación y autorización
- **JWT (JJWT) 0.12.3** - Implementación de JSON Web Tokens
- **BCrypt** - Algoritmo de hash para contraseñas

### Persistencia
- **Spring Data JPA** - Abstracción para acceso a datos
- **MySQL Connector J** - Driver para base de datos MySQL
- **Hibernate** - ORM (Object-Relational Mapping)

### Validación y Mapeo
- **Spring Boot Validation** - Validación de datos de entrada
- **MapStruct 1.5.5** - Mapeo automático entre objetos
- **Lombok 1.18.30** - Reducción de código boilerplate

### Web y API
- **Spring Boot Web** - Desarrollo de APIs REST
- **Jackson** - Serialización/deserialización JSON

### Testing
- **Spring Boot Test** - Framework de testing integrado
- **Spring Security Test** - Testing de componentes de seguridad

## Base de Datos

### Flexibilidad de Persistencia

Una de las **ventajas clave de la Arquitectura Hexagonal** es que permite cambiar fácilmente el tipo de base de datos sin afectar la lógica de negocio:

#### **Bases de Datos Soportadas**
- **SQL**: MySQL, PostgreSQL, SQL Server, Oracle
- **NoSQL**: MongoDB, Cassandra, DynamoDB
- **In-Memory**: H2, HSQLDB para testing

#### **Cambio de Base de Datos**
Para cambiar de MySQL a MongoDB, por ejemplo, solo necesitas:
1. Cambiar la dependencia en `pom.xml`
2. Implementar nuevos adaptadores de persistencia (StudentPersistenceAdapter, UserPersistenceAdapter)
3. La lógica de negocio permanece **intacta**

### Configuración Actual (MySQL)

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/student_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&createDatabaseIfNotExist=true
    username: root
    password: 
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: create
    defer-datasource-initialization: true
    show-sql: true
  sql:
    init:
      mode: always

security:
  jwt:
    secret: "mySecretKeyForJWTTokenGenerationAndValidation..."
    expiration: 86400000    # 24 horas
    refresh-expiration: 604800000  # 7 días
```

### Modelo de Datos

El sistema maneja las siguientes entidades principales:
- **Students**: Información académica de estudiantes
- **Users**: Usuarios del sistema con credenciales
- **Roles**: Roles de usuario (USER, ADMIN)
- **Permissions**: Permisos granulares (STUDENT_READ, STUDENT_WRITE, etc.)
- **Tokens**: Gestión de tokens JWT activos

## Seguridad y Autenticación

### Implementación de Seguridad JWT

El servicio implementa un **sistema de autenticación y autorización completo** usando **JSON Web Tokens (JWT)** y **Spring Security**, manteniendo los principios de la arquitectura hexagonal.

#### **Características de Seguridad**

- **Autenticación JWT** con tokens de acceso y refresh tokens
- **Autorización basada en roles** (USER, ADMIN)
- **Permisos granulares** por recurso y acción
- **Encriptación BCrypt** para contraseñas
- **Gestión de sesiones** sin estado (stateless)
- **Invalidación de tokens** en logout
- **Protección CSRF** deshabilitada para APIs REST
- **Configuración de CORS** para desarrollo

#### **Usuarios por Defecto**

| Usuario | Contraseña | Rol | Permisos |
|---------|------------|-----|----------|
| `admin` | `password` | ADMIN | Acceso completo (CRUD estudiantes + gestión usuarios) |
| `user` | `password` | USER | Solo lectura de estudiantes |

### Endpoints de Autenticación

| Método | Endpoint | Descripción | Autenticación |
|--------|----------|-------------|---------------|
| `POST` | `/auth/v1/api/login` | Iniciar sesión | Público |
| `POST` | `/auth/v1/api/refresh` | Renovar tokens | Público |
| `POST` | `/auth/v1/api/logout` | Cerrar sesión | Bearer Token |

### Endpoints Protegidos

| Método | Endpoint | Roles Permitidos |
|--------|----------|------------------|
| `GET` | `/students/v1/api/**` | USER, ADMIN |
| `POST` | `/students/v1/api` | ADMIN |
| `PUT` | `/students/v1/api/**` | ADMIN |
| `DELETE` | `/students/v1/api/**` | ADMIN |
| `GET` | `/users/v1/api/**` | ADMIN |
| `POST` | `/users/v1/api` | ADMIN |

### Flujo de Autenticación

```json
// 1. Login
POST /auth/v1/api/login
{
  "username": "admin",
  "password": "password"
}

// 2. Response con tokens
{
  "token": "eyJhbGciOiJIUzI1NiIs...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIs...",
  "tokenType": "Bearer",
  "expiresAt": "2024-12-06T10:30:00",
  "user": { ... }
}

// 3. Usar token para acceder a recursos
GET /students/v1/api
Authorization: Bearer eyJhbGciOiJIUzI1NiIs...

// 4. Renovar cuando expire
POST /auth/v1/api/refresh
{
  "refreshToken": "eyJhbGciOiJIUzI1NiIs..."
}

// 5. Logout
POST /auth/v1/api/logout
Authorization: Bearer eyJhbGciOiJIUzI1NaIs...
```

## Instalación y Configuración

### Prerrequisitos
- Java 21+
- Maven 3.8+
- MySQL 8.0+ (XAMPP recomendado para desarrollo local)

### Pasos de Instalación

1. **Clonar el repositorio**
   ```bash
   git clone <repository-url>
   cd student-service
   ```

2. **Configurar Base de Datos**
   ```sql
   CREATE DATABASE student_db;
   ```

3. **Configurar variables de entorno (Opcional para producción)**
   ```bash
   export SECURITY_JWT_SECRET=your-super-secret-key-here
   export DB_USERNAME=your-db-username
   export DB_PASSWORD=your-db-password
   ```

4. **Instalar dependencias**
   ```bash
   mvn clean install
   ```

5. **Ejecutar la aplicación**
   ```bash
   mvn spring-boot:run
   # O en Windows
   ./mvnw.cmd spring-boot:run
   ```

6. **Verificar instalación**
   ```bash
   curl -X POST http://localhost:8080/auth/v1/api/login \
     -H "Content-Type: application/json" \
     -d '{"username":"admin","password":"password"}'
   ```

## API Endpoints

### Gestión de Estudiantes (Protegidos)

| Método | Endpoint | Descripción | Roles | Headers Requeridos |
|--------|----------|-------------|-------|-------------------|
| `GET` | `/students/v1/api` | Obtener todos los estudiantes | USER, ADMIN | Authorization: Bearer {token} |
| `GET` | `/students/v1/api/{id}` | Obtener estudiante por ID | USER, ADMIN | Authorization: Bearer {token} |
| `POST` | `/students/v1/api` | Crear nuevo estudiante | ADMIN | Authorization: Bearer {token} |
| `PUT` | `/students/v1/api/{id}` | Actualizar estudiante | ADMIN | Authorization: Bearer {token} |
| `DELETE` | `/students/v1/api/{id}` | Eliminar estudiante | ADMIN | Authorization: Bearer {token} |

### Gestión de Usuarios (Solo ADMIN)

| Método | Endpoint | Descripción | Headers Requeridos |
|--------|----------|-------------|-------------------|
| `GET` | `/users/v1/api` | Obtener todos los usuarios | Authorization: Bearer {token} |
| `GET` | `/users/v1/api/{id}` | Obtener usuario por ID | Authorization: Bearer {token} |
| `POST` | `/users/v1/api` | Crear nuevo usuario | Authorization: Bearer {token} |
| `PUT` | `/users/v1/api/{id}/enable` | Habilitar usuario | Authorization: Bearer {token} |
| `PUT` | `/users/v1/api/{id}/disable` | Deshabilitar usuario | Authorization: Bearer {token} |
| `DELETE` | `/users/v1/api/{id}` | Eliminar usuario | Authorization: Bearer {token} |

### Ejemplo de Request para Estudiante

```json
POST /students/v1/api
Authorization: Bearer eyJhbGciOiJIUzI1NiIs...
Content-Type: application/json

{
    "firstname": "Juan",
    "lastname": "Pérez",
    "age": 22,
    "address": "Calle Principal 123"
}
```

### Ejemplo de Response

```json
{
    "id": 1,
    "first_name": "Juan",
    "last_name": "Pérez",
    "age": 22,
    "address": "Calle Principal 123"
}
```

### Ejemplo de Request para Usuario

```json
POST /users/v1/api
Authorization: Bearer eyJhbGciOiJIUzI1NiIs...
Content-Type: application/json

{
    "username": "newuser",
    "email": "newuser@example.com",
    "password": "securepassword123",
    "enabled": true
}
```

## Testing

### Ejecutar Tests

```bash
# Ejecutar todos los tests
mvn test

# Ejecutar tests con cobertura
mvn test jacoco:report

# Ejecutar solo tests de seguridad
mvn test -Dtest="*Security*"

# Ejecutar tests de integración
mvn test -Dtest="*Integration*"
```

### Testing de Seguridad

El proyecto incluye tests específicos para:
- Autenticación JWT
- Autorización basada en roles
- Validación de tokens
- Endpoints protegidos
- Casos de error de seguridad

## Beneficios de la Arquitectura Hexagonal

### **Encapsulamiento de Datos**
- Las capas están **completamente aisladas**
- Los datos fluyen únicamente a través de **interfaces bien definidas**
- Cambios en infraestructura **no afectan** la lógica de negocio
- **Seguridad transversal** sin acoplamiento

### **Testabilidad**
- **Fácil mockeo** de dependencias externas
- Tests unitarios **rápidos y confiables**
- Tests de integración **aislados**
- **Tests de seguridad** independientes

### **Flexibilidad**
- **Intercambio fácil** de bases de datos
- **Múltiples interfaces** (REST, GraphQL, CLI)
- **Diferentes providers de seguridad** (JWT, OAuth2, SAML)
- **Adaptable** a nuevos requerimientos

### **Mantenibilidad**
- **Código organizado** y predecible
- **Responsabilidades claramente separadas**
- **Fácil de escalar** y modificar
- **Seguridad centralizada** y gestionable

## Estructura del Proyecto

```
src/main/java/com/services/ms/student/student_app/
├── domain/                          # NÚCLEO DE NEGOCIO
│   ├── model/
│   │   ├── Student.java            # Entidad principal
│   │   ├── User.java               # Entidad de usuario
│   │   ├── Role.java               # Entidad de rol
│   │   ├── Permission.java         # Entidad de permiso
│   │   ├── AuthToken.java          # Token de autenticación
│   │   └── ErrorResponse.java      # Modelo de respuesta de error
│   └── exception/
│       ├── StudentNotFoundException.java
│       ├── UserNotFoundException.java
│       ├── InvalidCredentialsException.java
│       ├── TokenExpiredException.java
│       └── UnauthorizedException.java
├── application/                     # PUERTOS
│   ├── ports/
│   │   ├── input/
│   │   │   ├── StudentServicePort.java      # Puerto de entrada - estudiantes
│   │   │   ├── AuthenticationServicePort.java # Puerto - autenticación
│   │   │   ├── UserServicePort.java         # Puerto de entrada - usuarios
│   │   │   └── AuthorizationServicePort.java # Puerto - autorización
│   │   └── output/
│   │       ├── StudentPersistencePort.java  # Puerto de salida - estudiantes
│   │       ├── UserPersistencePort.java     # Puerto de salida - usuarios
│   │       ├── TokenPersistencePort.java    # Puerto de salida - tokens
│   │       └── JwtTokenPort.java            # Puerto de salida - JWT
│   └── service/
│       ├── StudentService.java             # Lógica de negocio - estudiantes
│       ├── AuthenticationService.java      # Lógica de negocio - autenticación
│       ├── UserService.java                # Lógica de negocio - usuarios
│       └── AuthorizationService.java       # Lógica de negocio - autorización
└── infrastructure/                 # ADAPTADORES
    ├── config/
    │   ├── SecurityConfig.java             # Configuración de seguridad
    │   ├── PasswordEncoderConfig.java      # Configuración de encriptación
    │   └── DataInitializer.java            # Inicialización de datos
    └── adapters/
        ├── input/
        │   └── rest/               # Adaptador REST
        │       ├── StudentRestAdapter.java
        │       ├── AuthRestAdapter.java     # Controlador de autenticación
        │       ├── UserRestAdapter.java     # Controlador de usuarios
        │       ├── GlobalControllerAdvice.java
        │       ├── mapper/
        │       │   ├── StudentRestMapper.java
        │       │   └── AuthRestMapper.java  # Mapper de autenticación
        │       └── model/
        │           ├── request/
        │           │   ├── StudentCreateRequest.java
        │           │   ├── LoginRequest.java
        │           │   ├── RefreshTokenRequest.java
        │           │   └── UserCreateRequest.java
        │           └── response/
        │               ├── StudentResponse.java
        │               ├── AuthResponse.java
        │               ├── UserResponse.java
        │               ├── RoleResponse.java
        │               └── PermissionResponse.java
        └── output/
            └── persistence/        # Adaptador de Persistencia
                ├── StudentPersistenceAdapter.java
                ├── UserPersistenceAdapter.java      # Adaptador - usuarios
                ├── TokenPersistenceAdapter.java     # Adaptador - tokens
                ├── JwtTokenAdapter.java             # Adaptador - JWT
                ├── entity/
                │   ├── StudentEntity.java
                │   ├── UserEntity.java              # Entidad JPA - usuario
                │   ├── RoleEntity.java              # Entidad JPA - rol
                │   ├── PermissionEntity.java        # Entidad JPA - permiso
                │   └── TokenEntity.java             # Entidad JPA - token
                ├── mapper/
                │   ├── StudentPersistenceMapper.java
                │   └── UserPersistenceMapper.java   # Mapper de persistencia - usuarios
                └── repository/
                    ├── StudentRepository.java
                    ├── UserJpaRepository.java       # Repositorio JPA - usuarios
                    └── TokenJpaRepository.java      # Repositorio JPA - tokens
```

## Autor

Proyecto desarrollado como estudio de la **Arquitectura Hexagonal** y sus beneficios en el desarrollo de software modular y mantenible, incluyendo la implementación de **sistemas de seguridad robustos** siguiendo los mismos principios arquitectónicos.