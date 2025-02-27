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

