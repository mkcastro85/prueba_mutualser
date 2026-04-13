# Employee Management System

## Arquitectura

```
src/
├── main/
│   ├── java/com/mutualser/employee/
│   │   ├── domain/                    # Capa de dominio (sin dependencias externas)
│   │   │   ├── model/                 # Entidades de dominio (Employee, User)
│   │   │   ├── port/                  # Interfaces/Puertos
│   │   │   └── exception/             # Excepciones de dominio
│   │   ├── application/               # Capa de aplicación
│   │   │   └── usecase/               # Casos de uso (lógica de negocio)
│   │   └── infrastructure/            # Capa de infraestructura
│   │       ├── adapter/               # Implementaciones de puertos
│   │       ├── config/                # Configuraciones Spring
│   │       ├── controller/            # REST Controllers
│   │       ├── dto/                   # DTOs
│   │       ├── entity/                # Entidades JPA
│   │       ├── repository/            # Repositorios JPA
│   │       ├── security/              # Filtros de seguridad
│   │       └── exception/             # Manejador global de excepciones
│   └── resources/
│       └── application.yml            # Configuración de la aplicación
└── test/                              # Tests unitarios
```

## Instalación y Ejecución

### 1. Clonar o descargar el proyecto

```bash
cd prueba_mutualser
```

### 2. Compilar el proyecto

```bash
mvn clean install
```

### 3. Ejecutar la aplicación

```bash
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`


## Ejecutar Tests

```bash
mvn test
```

### 1. Obtener token JWT

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123*"}'
```

### 2. Crear empleado

```bash
curl -X POST http://localhost:8080/api/employees \
  -H "Authorization: Bearer {tu-token-aqui}" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Ana",
    "lastName": "Martínez",
    "gender": "FEMALE",
    "age": 45,
    "email": "ana.martinez@example.com"
  }'
```
