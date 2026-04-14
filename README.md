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

### 2. Guardar empleado

```bash
curl -X POST http://localhost:8080/api/employees \
  -H "Authorization: Bearer {tu-token-aqui}" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Meyling",
    "lastName": "Castro",
    "gender": "FEMALE",
    "age": 28,
    "email": "meyling.castro@gmail.com"
  }'
```

### 3. Consultar empleado por ID

```bash
curl -X GET http://localhost:8080/api/employees/1 \
  -H "Authorization: Bearer {tu-token-aqui}"
```

### 4. Editar empleado

```bash
curl -X PUT http://localhost:8080/api/employees/1 \
  -H "Authorization: Bearer {tu-token-aqui}" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Meyling",
    "lastName": "Castro",
    "gender": "FEMALE",
    "age": 29,
    "email": "meyling.castro@gmail.com"
  }'
```

### 5. Eliminar empleado

```bash
curl -X DELETE http://localhost:8080/api/employees/1 \
  -H "Authorization: Bearer {tu-token-aqui}"
```

### 6. Consultar todos los empleados registrados

```bash
curl -X GET http://localhost:8080/api/employees \
  -H "Authorization: Bearer {tu-token-aqui}"
```

### 7. Consultar empleados con edad mayor o igual a 40 años

```bash
curl -X GET "http://localhost:8080/api/employees/age-filter?minAge=40" \
  -H "Authorization: Bearer {tu-token-aqui}"
```

### 8. Consultar empleados con sexo Femenino

```bash
curl -X GET http://localhost:8080/api/employees/gender/FEMALE \
  -H "Authorization: Bearer {tu-token-aqui}"
```

## Validaciones y Mensajes de Error

### Error de validación

```bash
curl -X POST http://localhost:8080/api/employees \
  -H "Authorization: Bearer {tu-token-aqui}" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "",
    "lastName": "Castro",
    "gender": "FEMALE",
    "age": 28,
    "email": "invalid-email"
  }'
```

**Respuesta:**
```json
{
  "code": "400",
  "message": "First name is required; Email must be valid"
}
```

### Empleado no encontrado

```bash
curl -X GET http://localhost:8080/api/employees/999 \
  -H "Authorization: Bearer {tu-token-aqui}"
```

**Respuesta:**
```json
{
  "code": "404",
  "message": "Employee not found with id: 999"
}
```

### Límite de empleados alcanzado

**Respuesta:**
```json
{
  "code": "400",
  "message": "Cannot create more employees. Maximum limit of 30 reached"
}
```