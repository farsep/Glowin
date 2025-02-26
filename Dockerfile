# Usar la imagen oficial de OpenJDK
FROM openjdk:23-jdk-slim

# Establecer el directorio de trabajo en el contenedor
WORKDIR /app

# Copiar todos los archivos del proyecto al contenedor
COPY . .

# Dar permisos de ejecución al wrapper de Maven (solo si usas mvnw)
RUN chmod +x mvnw

# Construir el proyecto y generar el archivo .jar dentro del contenedor
RUN ./mvnw clean package -DskipTests

# Mover el .jar generado a la raíz del contenedor y renombrarlo
RUN mv target/*.jar app.jar

# Exponer el puerto donde correrá la aplicación
EXPOSE 8080

# Comando para ejecutar el backend
CMD ["java", "-jar", "app.jar"]
