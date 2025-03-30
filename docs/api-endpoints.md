# 📌 API Endpoints


## 👥 Usuarios

### 🟢 Obtener un usuario por ID
**📌 Endpoint:** `GET /usuarios/{id}`  
**📚 Descripción:** Obtiene un usuario específico por su ID.

**👥 Parámetros de ruta:**

| Parámetro | Tipo  | Descripción |
|------------|------|-------------|
| id         | Long | ID del usuario a buscar. |

**💄 Respuestas:**
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

**💄 Respuestas:**
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
- `204 No Content` – No hay usuarios registrados.

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
- El campo `email` debe ser único en el sistema.
- `fechaRegistro` y `horaRegistro` son opcionales y se asignan por defecto en el servidor.
- Solo puede haber un `SUPER_ADMINISTRADOR` en el sistema.

**💄 Respuestas:**
- `201 Created` – Usuario creado exitosamente.
- `409 Conflict` – Si el email ya está en uso o si ya existe un `SUPER_ADMINISTRADOR`.
- `400 Bad Request` – Datos inválidos.
- `500 Internal Server Error` – Error al enviar el correo de confirmación.

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
**Notas:**
- Todos los campos son opcionales. Solo se actualizarán los que se incluyan en la solicitud.
- Si se actualiza el `email`, debe ser único.
- No se puede actualizar a `SUPER_ADMINISTRADOR` si ya existe uno.

**💄 Respuestas:**
- `200 OK` – Usuario actualizado correctamente.
- `404 Not Found` – Si el usuario no existe.
- `409 Conflict` – Si el nuevo email ya está en uso o se intenta asignar `SUPER_ADMINISTRADOR` cuando ya existe uno.

---

### 🟢 Eliminar un usuario
**📌 Endpoint:** `DELETE /usuarios/{id}`  
**📚 Descripción:** Elimina un usuario por su ID. Si el usuario es `SUPER_ADMINISTRADOR`, se debe proporcionar un `nuevoSuperAdminId` antes de eliminarlo.

**👥 Parámetros:**

| Parámetro | Tipo  | Descripción |
|------------|------|-------------|
| id         | Long | ID del usuario a eliminar. |
| nuevoSuperAdminId | Long | (Opcional) ID del nuevo `SUPER_ADMINISTRADOR` si se está eliminando al actual. |

**Notas:**
- Antes de eliminar un usuario, se eliminan todas sus reservas y favoritos.
- Si el usuario es `SUPER_ADMINISTRADOR`, debe existir otro usuario para asignarle el rol antes de eliminarlo.

**💄 Respuestas:**
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

Ejemplo de respuesta:
```json
[
  {
    "id": 1,
    "nombre": "Peluquería",
    "urlImagen": "https://ejemplo.com/imagen.jpg"
  }
]
```

---

### 🟢 Obtener una categoría por ID

**📌 Endpoint:** `GET /categorias-servicios/{id}`

**📚 Descripción:** Obtiene una categoría de servicio específica por su ID.

**👥 Parámetros:**

| Parámetro | Tipo | Descripción                 |
| -------------- | ---- | -------------------------------- |
| `id`           | Long | ID de la categoría a buscar |

**📤 Respuestas:**

- ✅ `200 OK` - Retorna la categoría en formato JSON.
- ❌ `404 Not Found` - Si la categoría no existe.

Ejemplo de respuesta:
```json
{
  "id": 1,
  "nombre": "Peluquería",
  "urlImagen": "https://ejemplo.com/imagen.jpg"
}
```

---

### 🟢 Actualizar una categoría

**📌 Endpoint:** `PUT /categorias-servicios/{id}`

**📚 Descripción:** Actualiza los datos de una categoría de servicio existente.

**👥 Parámetros:**

| Parámetro | Tipo | Descripción                     |
| -------------- | ---- | ------------------------------------ |
| `id`           | Long | ID de la categoría a actualizar |

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

Ejemplo de respuesta:
```json
{
  "id": 2,
  "nombre": "Spa",
  "urlImagen": "https://ejemplo.com/spa.jpg"
}
```

---

### 🟢 Eliminar una categoría

**📌 Endpoint:** `DELETE /categorias-servicios/{id}`

**📚 Descripción:** Elimina una categoría de servicio por su ID. Si la categoría tiene servicios asociados, estos también serán eliminados.

**👥 Parámetros:**

| Parámetro | Tipo | Descripción                   |
| -------------- | ---- | ---------------------------------- |
| `id`           | Long | ID de la categoría a eliminar |

**📤 Respuestas:**

- ✅ `200 OK` - Categoría y servicios eliminados correctamente.
- ❌ `404 Not Found` - Si la categoría no existe.

Ejemplo de respuesta:
```json
{
  "message": "Categoría y todos los servicios asociados eliminados con éxito",
  "status": "200",
  "timestamp": "2024-03-10"
}
```

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

## 🖼️ Imágenes de Servicios

### 🟢 Listar imágenes de un servicio
**📌 Endpoint:** `GET /imagenes-servicios/{idServicio}/imagenes`

**📖 Descripción:** Obtiene todas las imágenes asociadas a un servicio específico.

**📥 Parámetros:**
| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| `idServicio` | Long | ID del servicio |

**📤 Respuestas:**
- ✅ `200 OK` - Lista de imágenes en formato JSON.
- ❌ `404 Not Found` - Si el servicio no existe.

---

### 🟢 Obtener una imagen por ID
**📌 Endpoint:** `GET /imagenes-servicios/{idServicio}/imagenes/{idImagen}`

**📖 Descripción:** Obtiene una imagen específica de un servicio.

**📥 Parámetros:**
| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| `idServicio` | Long | ID del servicio |
| `idImagen` | Long | ID de la imagen |

**📤 Respuestas:**
- ✅ `200 OK` - Imagen en formato JSON.
- ❌ `404 Not Found` - Si la imagen no existe.
- ❌ `400 Bad Request` - Si la imagen no pertenece al servicio indicado.

---

### 🟢 Crear una imagen para un servicio
**📌 Endpoint:** `POST /imagenes-servicios/{idServicio}/imagenes`

**📖 Descripción:** Crea una nueva imagen y la asocia a un servicio.

**📥 Cuerpo de la solicitud (`JSON`):**
```json
{
  "titulo": "string",
  "descripcion": "string",
  "urlImagen": "string",
  "fechaCreacion": "yyyy-MM-dd (opcional)"
}
```

**📤 Respuestas:**
- ✅ `201 Created` - Imagen creada exitosamente.
- ❌ `400 Bad Request` - Si la solicitud es inválida.
- ❌ `404 Not Found` - Si el servicio no existe.

---

### 🟢 Actualizar una imagen
**📌 Endpoint:** `PUT /imagenes-servicios/{idServicio}/imagenes/{idImagen}`

**📖 Descripción:** Actualiza una imagen existente.

**📥 Cuerpo de la solicitud (`JSON`):**
```json
{
  "titulo": "string (opcional)",
  "descripcion": "string (opcional)",
  "urlImagen": "string (opcional)",
  "fechaCreacion": "yyyy-MM-dd (opcional)"
}
```

**📤 Respuestas:**
- ✅ `200 OK` - Imagen actualizada correctamente.
- ❌ `404 Not Found` - Si la imagen no existe.
- ❌ `400 Bad Request` - Si la imagen no pertenece al servicio.

---

### 🟢 Eliminar una imagen
**📌 Endpoint:** `DELETE /imagenes-servicios/{idServicio}/imagenes/{idImagen}`

**📖 Descripción:** Elimina una imagen de un servicio.

**📤 Respuestas:**
- ✅ `200 OK` - Imagen eliminada correctamente.
- ❌ `404 Not Found` - Si la imagen no existe.
- ❌ `400 Bad Request` - Si la imagen no pertenece al servicio.


---

## 📅 Reservas

### 🟢 Generar horarios disponibles por día
**📌 Método interno:** `generateDailySlots(LocalDate date)`  
**📚 Descripción:** Genera una lista de franjas horarias disponibles en un día específico.

**⚙️ Parámetros:**

| Parámetro | Tipo       | Descripción |
|-----------|-----------|-------------|
| date      | LocalDate | Fecha para la que se generarán los horarios disponibles. |

**💡 Detalles:**
- Se generan franjas horarias desde las `09:00` hasta las `21:00`.
- Cada franja tiene una duración de `1 hora`.

**🔄 Retorno:**
Devuelve una lista de objetos con el siguiente formato:
```json
[
  { "fecha": "2025-03-30", "hora": "09:00" },
  { "fecha": "2025-03-30", "hora": "10:00" }
]
```

---

### 🟢 Registrar una nueva reserva
**📌 Endpoint:** `POST /reservas`  
**📚 Descripción:** Registra una nueva reserva y envía un correo de confirmación al usuario.

**📥 Cuerpo de la solicitud (`JSON`):**
```json
{
  "idCliente": 1,
  "idServicio": 2,
  "idEmpleado": 3,
  "fecha": "2025-04-10",
  "hora": "14:00:00",
  "estado": "PENDIENTE"
}
```

**📜 Notas:**
- `idCliente`, `idServicio` e `idEmpleado` son obligatorios.
- `fecha` debe estar en formato `yyyy-MM-dd`.
- `hora` debe estar en formato `HH:mm:ss`.

**💄 Respuestas:**
- `201 Created` – Reserva creada y correo enviado exitosamente.
- `404 Not Found` – Usuario, servicio o empleado no encontrado.
- `500 Internal Server Error` – Error al enviar el correo.

---

### 🟢 Actualizar una reserva
**📌 Endpoint:** `PUT /reservas/{id}`  
**📚 Descripción:** Actualiza los datos de una reserva existente.

**👥 Parámetros de ruta:**

| Parámetro | Tipo  | Descripción |
|-----------|------|-------------|
| id        | Long | ID de la reserva a actualizar. |

**📥 Cuerpo de la solicitud (`JSON`):**
```json
{
  "idCliente": 1,
  "idServicio": 2,
  "idEmpleado": 3,
  "fecha": "2025-04-10",
  "hora": "14:00:00",
  "estado": "CONFIRMADA"
}
```

**📜 Notas:**
- Todos los campos son opcionales, solo se actualizarán los que se incluyan.
- Si `estado` cambia a `CONFIRMADA`, se enviará un correo de confirmación.
- Si `estado` cambia a `CANCELADA`, se enviará un correo notificando la cancelación.

**💄 Respuestas:**
- `200 OK` – Reserva actualizada correctamente.
- `404 Not Found` – Reserva o entidad relacionada no encontrada.
- `500 Internal Server Error` – Error al enviar el correo de confirmación o cancelación.


---

## ⭐ Favoritos

### 🟢 Marcar un servicio como favorito
**📌 Endpoint:** `POST /favoritos`

**📖 Descripción:** Permite a un usuario marcar un servicio como favorito.

**📥 Cuerpo de la solicitud (`JSON`):**
```json
{
  "idUsuario": "Long",
  "idServicio": "Long"
}
```  

**📤 Respuestas:**
- ✅ `200 OK` - Servicio agregado a favoritos exitosamente.
- ❌ `400 Bad Request` - Si el usuario o servicio no existen, o si el servicio ya está en favoritos.

---

### 🟢 Obtener favoritos de un usuario
**📌 Endpoint:** `GET /favoritos/usuario/{usuarioId}`

**📖 Descripción:** Obtiene la lista de servicios que un usuario ha marcado como favoritos.

**📥 Parámetros:**  
| Parámetro   | Tipo  | Descripción                           |  
|------------|------|---------------------------------------|  
| `usuarioId` | Long | ID del usuario del que se listarán sus favoritos. |

**📤 Respuestas:**
- ✅ `200 OK` - Lista de favoritos en formato JSON.
- ❌ `400 Bad Request` - Si el usuario no existe.

**📥 Ejemplo de respuesta (`JSON`):**
```json
[
  {
    "id": 1,
    "idUsuario": 5,
    "idServicio": 12,
    "fechaAgregado": "2024-03-10"
  }
]
```


---


### 🟢 Eliminar un favorito
**📌 Endpoint:** `DELETE /favoritos/{id}`

**📖 Descripción:** Elimina un servicio de la lista de favoritos de un usuario.

**📥 Parámetros:**  
| Parámetro | Tipo  | Descripción |  
|-----------|------|-------------|  
| `id`      | Long | ID del favorito a eliminar. |

**📤 Respuestas:**
- ✅ `200 OK` - Favorito eliminado correctamente.
- ❌ `400 Bad Request` - Si el favorito no existe.

---

## API de Servicios y Empleados

### **Obtener IDs de servicios de un empleado**
### 🔹 `GET /empleado/{idEmpleado}/servicios`
### 📌 Descripción:
Recupera todos los **IDs de servicios** que ofrece un **empleado** dado su `idEmpleado`.

### ✅ **Parámetros**:
| Parámetro   | Tipo   | Requerido | Descripción               |
|------------|--------|-----------|---------------------------|
| idEmpleado | Long   | Sí        | ID del empleado a consultar |

### 🔄 **Respuestas**:
| Código | Descripción                      |
|--------|----------------------------------|
| `200`  | Lista de IDs de servicios encontrada |
| `404`  | Empleado no encontrado           |

### 🔍 **Ejemplo de Respuesta `200`**:
```json
[1, 2, 5, 7]
```

---

### **Obtener IDs de empleados que ofrecen un servicio**
### 🔹 `GET /servicio/{idServicio}/empleados`
### 📌 Descripción:
Recupera todos los **IDs de empleados** que ofrecen un **servicio** dado su `idServicio`.

### ✅ **Parámetros**:
| Parámetro  | Tipo   | Requerido | Descripción             |
|------------|--------|-----------|-------------------------|
| idServicio | Long   | Sí        | ID del servicio a consultar |

### 🔄 **Respuestas**:
| Código | Descripción                         |
|--------|-------------------------------------|
| `200`  | Lista de IDs de empleados encontrada |
| `404`  | Servicio no encontrado             |

### 🔍 **Ejemplo de Respuesta `200`**:
```json
[3, 8, 12]
```

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

⏳ **Última actualización:** 2024-03-18