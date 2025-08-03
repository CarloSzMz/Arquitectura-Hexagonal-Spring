# Student Service - Arquitectura Hexagonal

## 📋 Descripción

Este proyecto es un **estudio práctico de la Arquitectura Hexagonal** (también conocida como Ports and Adapters), implementado como un microservicio de gestión de estudiantes. El objetivo principal es demostrar cómo esta arquitectura permite el **encapsulamiento de datos entre capas** y proporciona una separación clara de responsabilidades.

La Arquitectura Hexagonal nos permite crear aplicaciones que son **independientes de frameworks externos**, facilitando el intercambio de adaptadores sin afectar la lógica de negocio central.

## 🏗️ Arquitectura Hexagonal

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
│  │ GraphQL     │────│ APPLICATION │────│ MESSAGE     │  │
│  │ CLI         │    │   (PORTS)   │    │ QUEUE       │  │
│  │             │    └─────────────┘    │ EMAIL       │  │
│  └─────────────┘                       └─────────────┘  │
└─────────────────────────────────────────────────────────┘
```

### Capas del Proyecto

#### 🎯 **Domain (Núcleo)**
- **Entities**: Modelos de negocio puros
- **Value Objects**: Objetos inmutables
- **Exceptions**: Excepciones específicas del dominio

#### 🚪 **Application (Puertos)**
- **Ports**: Interfaces que definen contratos
  - **Input Ports**: Casos de uso
  - **Output Ports**: Repositorios y servicios externos
- **Services**: Implementación de la lógica de negocio

#### 🔌 **Infrastructure (Adaptadores)**
- **Input Adapters**: Controladores REST, CLI, etc.
- **Output Adapters**: Repositorios JPA, clientes HTTP, etc.

## 🛠️ Tecnologías y Dependencias

### Framework Principal
- **Spring Boot 3.5.4** - Framework principal de Java
- **Java 21** - Versión LTS de Java

### Persistencia
- **Spring Data JPA** - Abstracción para acceso a datos
- **MySQL Connector** - Driver para base de datos MySQL
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

## 📊 Base de Datos

### Flexibilidad de Persistencia

Una de las **ventajas clave de la Arquitectura Hexagonal** es que permite cambiar fácilmente el tipo de base de datos sin afectar la lógica de negocio:

#### ✅ **Bases de Datos Soportadas**
- **SQL**: MySQL, PostgreSQL, SQL Server, Oracle
- **NoSQL**: MongoDB, Cassandra, DynamoDB
- **In-Memory**: H2, HSQLDB para testing

#### 🔄 **Cambio de Base de Datos**
Para cambiar de MySQL a MongoDB, por ejemplo, solo necesitas:
1. Cambiar la dependencia en `pom.xml`
2. Implementar un nuevo `StudentPersistenceAdapter`
3. La lógica de negocio permanece **intacta**

### Configuración Actual (MySQL)

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/student_db
    username: root
    password: 
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: create
    show-sql: true
```

## 🚀 Instalación y Configuración

### Prerrequisitos
- Java 21+
- Maven 3.8+
- MySQL (XAMPP recomendado para desarrollo local)

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

3. **Instalar dependencias**
   ```bash
   mvn clean install
   ```

4. **Ejecutar la aplicación**
   ```bash
   mvn spring-boot:run
   ```

## 📡 API Endpoints

### Gestión de Estudiantes

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/students/v1/api` | Obtener todos los estudiantes |
| `GET` | `/students/v1/api/{id}` | Obtener estudiante por ID |
| `POST` | `/students/v1/api` | Crear nuevo estudiante |
| `PUT` | `/students/v1/api/{id}` | Actualizar estudiante |
| `DELETE` | `/students/v1/api/{id}` | Eliminar estudiante |

### Ejemplo de Request

```json
POST /students/v1/api
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

## 🧪 Testing

```bash
# Ejecutar todos los tests
mvn test

# Ejecutar tests con cobertura
mvn test jacoco:report
```

## 📈 Beneficios de la Arquitectura Hexagonal

### 🔒 **Encapsulamiento de Datos**
- Las capas están **completamente aisladas**
- Los datos fluyen únicamente a través de **interfaces bien definidas**
- Cambios en infraestructura **no afectan** la lógica de negocio

### 🧪 **Testabilidad**
- **Fácil mockeo** de dependencias externas
- Tests unitarios **rápidos y confiables**
- Tests de integración **aislados**

### 🔄 **Flexibilidad**
- **Intercambio fácil** de bases de datos
- **Múltiples interfaces** (REST, GraphQL, CLI)
- **Adaptable** a nuevos requerimientos

### 🛡️ **Mantenibilidad**
- **Código organizado** y predecible
- **Responsabilidades claramente separadas**
- **Fácil de escalar** y modificar

## 🔍 Estructura del Proyecto

```
src/main/java/com/services/ms/student/student_app/
├── domain/                          # 🎯 NÚCLEO DE NEGOCIO
│   ├── model/
│   │   ├── Student.java            # Entidad principal
│   │   └── ErrorResponse.java      # Modelo de respuesta de error
│   └── exception/
│       └── StudentNotFoundException.java
├── application/                     # 🚪 PUERTOS
│   ├── ports/
│   │   ├── input/
│   │   │   └── StudentServicePort.java      # Puerto de entrada
│   │   └── output/
│   │       └── StudentPersistencePort.java  # Puerto de salida
│   └── service/
│       └── StudentService.java             # Lógica de negocio
└── infrastructure/                 # 🔌 ADAPTADORES
    └── adapters/
        ├── input/
        │   └── rest/               # Adaptador REST
        │       ├── StudentRestAdapter.java
        │       ├── GlobalControllerAdvice.java
        │       ├── mapper/
        │       │   └── StudentRestMapper.java
        │       └── model/
        │           ├── request/
        │           │   └── StudentCreateRequest.java
        │           └── response/
        │               └── StudentResponse.java
        └── output/
            └── persistence/        # Adaptador de Persistencia
                ├── StudentPersistenceAdapter.java
                ├── entity/
                │   └── StudentEntity.java
                ├── mapper/
                │   └── StudentPersistenceMapper.java
                └── repository/
                    └── StudentRepository.java
```

## 👨‍💻 Autor

Proyecto desarrollado como estudio de la **Arquitectura Hexagonal** y sus beneficios en el desarrollo de software modular y mantenible.

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE.md](LICENSE.md) para detalles.