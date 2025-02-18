# 🏗 Arquitectura del Proyecto Glowin

## 📌 Descripción General
Glowin es una aplicación desarrollada en **Spring Boot** para gestionar reservas en un salón de belleza. Utiliza **PostgreSQL** como base de datos y se implementará en **AWS**.

## 🛠️ Tecnologías Utilizadas
- **Backend**: Spring Boot 3.4.2
- **Base de Datos**: PostgreSQL
- **ORM**: JPA (Hibernate)
- **Migrations**: Flyway
- **Build Tool**: Maven
- **Infraestructura**: AWS

## 🏛️ Arquitectura de Capas
El backend sigue un enfoque basado en capas:
1. **Controller Layer**: Expone los endpoints REST.
2. **Service Layer**: Contiene la lógica de negocio.
3. **Repository Layer**: Maneja la persistencia con JPA.

## 🗃️ Base de Datos
- **Gestor**: PostgreSQL
- **Conexión**: `JDBC`
- **Migrations**: Flyway
- **Configuración**:
  ```properties
  spring.datasource.url=jdbc:postgresql://${localhost}:${port}/glowin
  spring.datasource.username=${username_db}
  spring.datasource.password=${password_db}
  ```

## 🔁 Gestión de Transacciones
- Uso de **Spring Data JPA** para operaciones transaccionales.
- Se define `@Transactional` en los métodos críticos.

## 🔄 Migraciones con Flyway
Flyway gestiona los cambios en la base de datos, asegurando la integridad y versionamiento.

## 🔗 Seguridad y Validación
- Se implementa **spring-boot-starter-validation** para validar datos.
- Manejo de excepciones a nivel global para respuestas consistentes.

## 🚀 Despliegue en AWS
- La infraestructura estará en **AWS**.
- Se debe definir cómo se manejarán los entornos (dev, staging, prod).

⚠️ *Nota:* La tecnología del frontend aún no está definida y será desarrollada por otro equipo.

---
📌 *Este documento se actualizará conforme avancen las decisiones técnicas.*

