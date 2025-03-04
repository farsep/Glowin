# 📌 API Endpoints


## 👥 Usuarios

### 🟢 Obtener un usuario por ID
**📌 Endpoint:** `GET /usuarios/{id}`  
**📚 Descripción:** Obtiene un usuario específico por su ID.

**👥 Parámetros de ruta:**

| Parámetro | Tipo  | Descripción |
|------------|------|-------------|
| id         | Long | ID del usuario a buscar. |

**📤 Respuestas:**
- `200 OK` – Retorna el usuario en formato JSON, por ejemplo:
```json
{
  "id": 1,
  "nombre": "Juan",
  "apellido": "Pérez",
  "rol": "CLIENTE"
}
```
- `404 Not Found` – Si el usuario no existe.

---

### 🟢 Obtener todos los usuarios
**📌 Endpoint:** `GET /usuarios/all`
**📚 Descripción:** Obtiene la lista de todos los usuarios registrados.

**📤 Respuestas:**
- `200 OK` – Retorna una lista de usuarios en formato JSON, por ejemplo:
```json
[
  {
    "id": 1,
    "nombre": "Juan",
    "apellido": "Pérez",
    "rol": "CLIENTE"
  },
  {
    "id": 2,
    "nombre": "María",
    "apellido": "Gómez",
    "rol": "ADMINISTRADOR"
  }
]
```

---

### 🟢 Registrar un usuario
**📌 Endpoint:** `POST /usuarios`
**📚 Descripción:** Registra un nuevo usuario en el sistema y envía un correo de confirmación.

**👥 Cuerpo de la solicitud (`JSON`):**
```json
{
  "nombre": "string",
  "apellido": "string",
  "email": "string",
  "celular": "string",
  "password": "string",
  "rol": "SUPER_ADMINISTRADOR | ADMINISTRADOR | CLIENTE",
  "fechaRegistro": "yyyy-MM-dd",
  "horaRegistro": "HH:mm:ss"
}
```
**Notas:**
- El campo `rol` define el tipo de usuario.
- `fechaRegistro` y `horaRegistro` son opcionales; si no se envían, se asignan valores por defecto en el servidor.

**📤 Respuestas:**
- `201 Created` – Usuario creado exitosamente.
- `409 Conflict` – Si el email ya está en uso o si ya existe un usuario con el rol `SUPER_ADMINISTRADOR`.

---

### 🟢 Actualizar un usuario
**📌 Endpoint:** `PUT /usuarios/{id}`
**📚 Descripción:** Actualiza los datos de un usuario existente.

**👥 Parámetros de ruta:**

| Parámetro | Tipo  | Descripción |
|------------|------|-------------|
| id         | Long | ID del usuario a actualizar. |

**👥 Cuerpo de la solicitud (`JSON`):**
```json
{
  "nombre": "string",
  "apellido": "string",
  "email": "string",
  "celular": "string",
  "password": "string",
  "rol": "SUPER_ADMINISTRADOR | ADMINISTRADOR | CLIENTE",
  "fechaRegistro": "yyyy-MM-dd",
  "horaRegistro": "HH:mm:ss"
}
```
Todos los campos son opcionales. Solo se actualizarán aquellos que se incluyan.

**📤 Respuestas:**
- `200 OK` – Usuario actualizado correctamente.
- `404 Not Found` – Si el usuario no existe.
- `409 Conflict` – Si se intenta:
    - Asignar un email que ya está en uso por otro usuario.
    - Asignar el rol `SUPER_ADMINISTRADOR` cuando ya existe uno en la base de datos.

---

### 🟢 Eliminar un usuario
**📌 Endpoint:** `DELETE /usuarios/{id}`
**📚 Descripción:** Elimina un usuario por su ID. Si se intenta eliminar un usuario con rol `SUPER_ADMINISTRADOR`, se debe proporcionar un `nuevoSuperAdminId` para transferir el rol antes de eliminarlo.

**👥 Parámetros:**

| Parámetro | Tipo  | Descripción |
|------------|------|-------------|
| id         | Long | ID del usuario a eliminar. |
| nuevoSuperAdminId | Long | (Opcional) ID del nuevo `SUPER_ADMINISTRADOR` si se está eliminando al actual. |

**📤 Respuestas:**
- `200 OK` – Usuario eliminado correctamente.
- `404 Not Found` – Si el usuario no existe.
- `409 Conflict` – Si el usuario es `SUPER_ADMINISTRADOR` y no se ha asignado uno nuevo.


---


## 👷 Empleados

### 🟢 Obtener un empleado por ID
**📌 Endpoint:** `GET /empleados/{id}`

**📖 Descripción:** Obtiene un empleado específico por su ID.

**📥 Parámetros:**
| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| `id` | Long | ID del empleado a buscar |

**📤 Respuestas:**
- ✅ `200 OK` - Retorna el empleado en formato JSON.
- ❌ `404 Not Found` - Si el empleado no existe.

---

### 🟢 Obtener todos los empleados
**📌 Endpoint:** `GET /empleados/all`

**📖 Descripción:** Obtiene la lista de todos los empleados registrados.

**📤 Respuestas:**
- ✅ `200 OK` - Retorna una lista de empleados en formato JSON.

---

### 🟢 Registrar un empleado
**📌 Endpoint:** `POST /empleados`

**📖 Descripción:** Registra un nuevo empleado en el sistema.

**📥 Cuerpo de la solicitud (`JSON`):**
```json
{
  "nombre": "string",
  "apellido": "string",
  "email": "string",
  "celular": "string",
  "salario": "number",
  "dni": "string",
  "fechaRegistro": "yyyy-MM-dd",
  "tipoJornada": "MATUTINA | VESPERTINA"
}
```

**📤 Respuestas:**
- ✅ `201 Created` - Empleado creado exitosamente.

---

### 🟢 Actualizar un empleado
**📌 Endpoint:** `PUT /empleados/{id}`

**📖 Descripción:** Actualiza los datos de un empleado existente.

**📥 Parámetros:**
| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| `id` | Long | ID del empleado a actualizar |

**📥 Cuerpo de la solicitud (`JSON`):**
```json
{
  "nombre": "string",
  "apellido": "string",
  "email": "string",
  "celular": "string",
  "salario": "number",
  "dni": "string",
  "fechaRegistro": "yyyy-MM-dd",
  "tipoJornada": "MATUTINA | VESPERTINA"
}
```

**📤 Respuestas:**
- ✅ `200 OK` - Empleado actualizado correctamente.
- ❌ `404 Not Found` - Si el empleado no existe.

---

### 🟢 Eliminar un empleado
**📌 Endpoint:** `DELETE /empleados/{id}`

**📖 Descripción:** Elimina un empleado por su ID.

**📥 Parámetros:**
| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| `id` | Long | ID del empleado a eliminar |

**📤 Respuestas:**
- ✅ `200 OK` - Empleado eliminado correctamente.
- ❌ `404 Not Found` - Si el empleado no existe.

---

## 📂 Categorías de Servicios

### 🟢 Obtener todas las categorías
**📌 Endpoint:** `GET /categorias-servicios/all`

**📚 Descripción:** Obtiene una lista de todas las categorías de servicios disponibles.

**📤 Respuestas:**
- ✅ `200 OK` - Retorna una lista de categorías en formato JSON.

---

### 🟢 Obtener una categoría por ID
**📌 Endpoint:** `GET /categorias-servicios/{id}`

**📚 Descripción:** Obtiene una categoría de servicio específica por su ID.

**👥 Parámetros:**
| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| `id` | Long | ID de la categoría a buscar |

**📤 Respuestas:**
- ✅ `200 OK` - Retorna la categoría en formato JSON.
- ❌ `404 Not Found` - Si la categoría no existe.

---

### 🟢 Actualizar una categoría
**📌 Endpoint:** `PUT /categorias-servicios/{id}`

**📚 Descripción:** Actualiza los datos de una categoría de servicio existente.

**👥 Parámetros:**
| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| `id` | Long | ID de la categoría a actualizar |

**📥 Cuerpo de la solicitud (`JSON`):**
```json
{
  "nombre": "string",
  "urlImagen": "string"
}
```

**📤 Respuestas:**
- ✅ `200 OK` - Categoría actualizada exitosamente.
- ❌ `404 Not Found` - Si la categoría no existe.
- ❌ `400 Bad Request` - Si los datos enviados no son válidos.

---

### 🟢 Registrar una nueva categoría
**📌 Endpoint:** `POST /categorias-servicios`

**📚 Descripción:** Registra una nueva categoría de servicio.

**📥 Cuerpo de la solicitud (`JSON`):**
```json
{
  "nombre": "string",
  "urlImagen": "string"
}
```

**📤 Respuestas:**
- ✅ `201 Created` - Categoría creada exitosamente.
- ❌ `400 Bad Request` - Si los datos enviados no son válidos.

---

### 🟢 Eliminar una categoría
**📌 Endpoint:** `DELETE /categorias-servicios/{id}`

**📚 Descripción:** Elimina una categoría de servicio por su ID.

**👥 Parámetros:**
| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| `id` | Long | ID de la categoría a eliminar |

**📤 Respuestas:**
- ✅ `200 OK` - Categoría eliminada correctamente.
- ❌ `404 Not Found` - Si la categoría no existe.

---

### 🟢 Manejo de Errores
#### ❌ Error de validación
Si los datos enviados en una solicitud no cumplen con las reglas de validación, se retornará un `400 Bad Request` con un cuerpo de respuesta en el siguiente formato:

```json
{
  "campo": "Mensaje de error"
}
```

Ejemplo:
```json
{
  "nombre": "El nombre es obligatorio"
}
```

---





---

## 🛠️ Servicios

### 🟢 Obtener un servicio por ID
**📌 Endpoint:** `GET /servicios/{id}`

**📖 Descripción:** Obtiene un servicio específico por su ID.

**📥 Parámetros:**
| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| `id` | Long | ID del servicio a buscar |

**📤 Respuestas:**
- ✅ `200 OK` - Retorna el servicio en formato JSON.
- ❌ `404 Not Found` - Si el servicio no existe.

---

### 🟢 Obtener todos los servicios
**📌 Endpoint:** `GET /servicios/all`

**📖 Descripción:** Obtiene la lista de todos los servicios registrados.

**📤 Respuestas:**
- ✅ `200 OK` - Retorna una lista de servicios en formato JSON.

---

### 🟢 Registrar un servicio
**📌 Endpoint:** `POST /servicios`

**📖 Descripción:** Registra un nuevo servicio en el sistema.
🔹 **Se puede proporcionar una categoría de dos formas:** enviando el `categoriaId` o enviando un objeto `categoria` con los datos de la nueva categoría.

**📥 Cuerpo de la solicitud (`JSON`):**
```json
{
  "nombre": "string",
  "descripcion": "string",
  "duracionMinutos": "integer",
  "costo": "number",
  "cantidadSesiones": "integer",
  "categoriaId": "Long (opcional)",
  "categoria": {
    "nombre": "string (opcional)"
  }
}
```
🔹 **Debe incluir `categoriaId` (ID de la categoría existente) o un objeto `categoria` con los datos de la nueva categoría.**

**📤 Respuestas:**
- ✅ `201 Created` - Servicio creado exitosamente.
- ❌ `400 Bad Request` - Si no se proporciona `categoriaId` ni `categoria`.

---

### 🟢 Actualizar un servicio
**📌 Endpoint:** `PUT /servicios/{id}`

**📖 Descripción:** Actualiza los datos de un servicio existente.

**📥 Parámetros:**
| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| `id` | Long | ID del servicio a actualizar |

**📥 Cuerpo de la solicitud (`JSON`):**
```json
{
  "nombre": "string",
  "descripcion": "string",
  "duracionMinutos": "integer",
  "costo": "number",
  "cantidadSesiones": "integer",
  "categoriaId": "Long (opcional)"
}
```

**📤 Respuestas:**
- ✅ `200 OK` - Servicio actualizado correctamente.
- ❌ `404 Not Found` - Si el servicio no existe.
- ❌ `400 Bad Request` - Si `categoriaId` no existe.

---

### 🟢 Eliminar un servicio
**📌 Endpoint:** `DELETE /servicios/{id}`

**📖 Descripción:** Elimina un servicio por su ID.

**📥 Parámetros:**
| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| `id` | Long | ID del servicio a eliminar |

**📤 Respuestas:**
- ✅ `200 OK` - Servicio eliminado correctamente.
- ❌ `404 Not Found` - Si el servicio no existe.


---

## 🔑 Autenticación

### 🟢 Login de usuario
**📌 Endpoint:** `POST /auth/login`  
**📚 Descripción:** Permite a un usuario iniciar sesión con su email y password.

**👥 Cuerpo de la solicitud (`JSON`):**
```json
{
  "email": "string",
  "password": "string"
}
```

**📤 Respuestas:**

- `200 OK` – Login exitoso. Retorna un objeto con:
```json
{
  "message": "Login exitoso",
  "rol": "SUPER_ADMINISTRADOR | ADMINISTRADOR | CLIENTE",
  "redirectUrl": "/dashboard/superadmin | /dashboard/admin | /home"
}
```
- `404 Not Found` – Si no se encuentra un usuario con el correo proporcionado.

---

### 🟢 Reenvío de correo de confirmación
**📌 Endpoint:** `POST /auth/resend-confirmation`
**📚 Descripción:** Permite reenviar el correo de confirmación a un usuario que ya se registró pero no recibió o perdió su correo de confirmación.

**👥 Cuerpo de la solicitud (`JSON`):**
```json
{
  "email": "string"
}
```
Donde `email` es el correo con el que el usuario se registró.

**📤 Respuestas:**
- `200 OK` – Se reenvía el correo de confirmación exitosamente.
```json
"Correo de confirmación reenviado a usuario@example.com"
```
- `404 Not Found` – Si no existe un usuario con el correo proporcionado.

---

⏳ **Última actualización:** ${LocalDate.now()}