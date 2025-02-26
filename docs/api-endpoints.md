# 📌 API de Usuarios

## 📍 Base URL
```
http://localhost:8080/usuarios
```

---

## **🟢 Obtener Usuario por ID**
### **GET /usuarios/{id}**
**Descripción:** Obtiene un usuario por su ID.

**📥 Parámetros de ruta:**
| Parámetro | Tipo  | Descripción |
|-----------|------|-------------|
| `id` | Long | ID del usuario |

**📤 Respuestas:**
- ✅ `200 OK` - Devuelve un JSON con los datos del usuario.
- ❌ `404 Not Found` - Si el usuario no existe.

**📌 Ejemplo de respuesta (200 OK):**
```json
{
  "id": 1,
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan@example.com",
  "celular": "123456789",
  "rol": "ADMINISTRADOR",
  "fechaRegistro": "2025-02-26",
  "horaRegistro": "14:30:00"
}
```

---

## **🟢 Obtener Todos los Usuarios**
### **GET /usuarios/all**
**Descripción:** Obtiene una lista de todos los usuarios.

**📤 Respuestas:**
- ✅ `200 OK` - Devuelve un array de usuarios.

**📌 Ejemplo de respuesta (200 OK):**
```json
[
  {
    "id": 1,
    "nombre": "Juan",
    "apellido": "Pérez",
    "email": "juan@example.com",
    "celular": "123456789",
    "rol": "ADMINISTRADOR",
    "fechaRegistro": "2025-02-26",
    "horaRegistro": "14:30:00"
  },
  {
    "id": 2,
    "nombre": "Ana",
    "apellido": "Gómez",
    "email": "ana@example.com",
    "celular": "987654321",
    "rol": "USUARIO",
    "fechaRegistro": "2025-02-26",
    "horaRegistro": "15:00:00"
  }
]
```

---

## **🟢 Registrar un Nuevo Usuario**
### **POST /usuarios**
**Descripción:** Crea un nuevo usuario.

**📥 Cuerpo de la solicitud (`JSON`):**
```json
{
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan@example.com",
  "celular": "123456789",
  "password": "password123",
  "rol": "ADMINISTRADOR"
}
```

**📤 Respuestas:**
- ✅ `201 Created` - Devuelve el usuario creado.
- ❌ `409 Conflict` - Si ya existe un `SUPER_ADMINISTRADOR`.

---

## **🟢 Actualizar un Usuario**
### **PUT /usuarios/{id}**
**Descripción:** Modifica datos de un usuario existente.

**📥 Parámetros de ruta:**
| Parámetro | Tipo  | Descripción |
|-----------|------|-------------|
| `id` | Long | ID del usuario |

**📥 Cuerpo de la solicitud (`JSON`):**
```json
{
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan@example.com",
  "celular": "987654321"
}
```

**📤 Respuestas:**
- ✅ `200 OK` - Devuelve el usuario actualizado.
- ❌ `404 Not Found` - Si el usuario no existe.
- ❌ `409 Conflict` - Si se intenta asignar `SUPER_ADMINISTRADOR` cuando ya hay uno.

---

## **🟢 Eliminar un Usuario**
### **DELETE /usuarios/{id}**
**Descripción:** Elimina un usuario por su ID.

**📥 Parámetros de ruta:**
| Parámetro | Tipo  | Descripción |
|-----------|------|-------------|
| `id` | Long | ID del usuario |

**📤 Respuestas:**
- ✅ `200 OK` - Usuario eliminado correctamente.
- ❌ `404 Not Found` - Si el usuario no existe.

**📌 Ejemplo de respuesta (200 OK):**
```json
{
  "message": "Usuario eliminado con éxito",
  "status": "200",
  "timestamp": "2025-02-26"
}
```

