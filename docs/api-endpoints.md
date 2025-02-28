# 📌 API Endpoints

## 👥 Usuarios

### 🟢 Obtener un usuario por ID
**📌 Endpoint:** `GET /usuarios/{id}`

**📖 Descripción:** Obtiene un usuario específico por su ID.

**📥 Parámetros:**
| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| `id` | Long | ID del usuario a buscar |

**📤 Respuestas:**
- ✅ `200 OK` - Retorna el usuario en formato JSON.
- ❌ `404 Not Found` - Si el usuario no existe.

---

### 🟢 Obtener todos los usuarios
**📌 Endpoint:** `GET /usuarios/all`

**📖 Descripción:** Obtiene la lista de todos los usuarios registrados.

**📤 Respuestas:**
- ✅ `200 OK` - Retorna una lista de usuarios en formato JSON.

---

### 🟢 Registrar un usuario
**📌 Endpoint:** `POST /usuarios`

**📖 Descripción:** Registra un nuevo usuario en el sistema.

**📥 Cuerpo de la solicitud (`JSON`):**
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

**📤 Respuestas:**
- ✅ `201 Created` - Usuario creado exitosamente.
- ❌ `409 Conflict` - Si ya existe un usuario con el rol `SUPER_ADMINISTRADOR`.

---

### 🟢 Actualizar un usuario
**📌 Endpoint:** `PUT /usuarios/{id}`

**📖 Descripción:** Actualiza los datos de un usuario existente.

**📥 Parámetros:**
| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| `id` | Long | ID del usuario a actualizar |

**📥 Cuerpo de la solicitud (`JSON`):**
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

**📤 Respuestas:**
- ✅ `200 OK` - Usuario actualizado correctamente.
- ❌ `404 Not Found` - Si el usuario no existe.
- ❌ `409 Conflict` - Si se intenta asignar el rol `SUPER_ADMINISTRADOR` y ya existe otro.

---

### 🟢 Eliminar un usuario
**📌 Endpoint:** `DELETE /usuarios/{id}`

**📖 Descripción:** Elimina un usuario por su ID.

**📥 Parámetros:**
| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| `id` | Long | ID del usuario a eliminar |

**📤 Respuestas:**
- ✅ `200 OK` - Usuario eliminado correctamente.
- ❌ `404 Not Found` - Si el usuario no existe.

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

**📚 Descripción:** Obtiene la lista de todas las categorías de servicios registradas.

#### 👤 Respuestas
- ✅ `200 OK` - Retorna una lista de categorías en formato JSON.

---

### 🟢 Obtener una categoría por ID
**📌 Endpoint:** `GET /categorias-servicios/{id}`

**📚 Descripción:** Obtiene una categoría específica por su ID.

#### 👤 Parámetros
| Parámetro | Tipo  | Descripción                      |
|-----------|------|--------------------------------|
| `id`      | Long | ID de la categoría a buscar. |

#### 👤 Respuestas
- ✅ `200 OK` - Retorna la categoría en formato JSON.
- ❌ `404 Not Found` - Si la categoría no existe.

---

### 🟢 Registrar una nueva categoría
**📌 Endpoint:** `POST /categorias-servicios`

**📚 Descripción:** Registra una nueva categoría en el sistema.  
🔹 **El nombre de la categoría se guardará automáticamente en mayúsculas.**

#### 📂 Cuerpo de la solicitud (`JSON`)
```json
{
  "nombre": "string"
}
```

#### 👤 Respuestas
- ✅ `201 Created` - Categoría creada exitosamente.

---

### 🟢 Eliminar una categoría
**📌 Endpoint:** `DELETE /categorias-servicios/{id}`

**📚 Descripción:** Elimina una categoría por su ID.

#### 👤 Parámetros
| Parámetro | Tipo  | Descripción                      |
|-----------|------|--------------------------------|
| `id`      | Long | ID de la categoría a eliminar. |

#### 👤 Respuestas
- ✅ `200 OK` - Categoría eliminada correctamente.
- ❌ `404 Not Found` - Si la categoría no existe.  

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

