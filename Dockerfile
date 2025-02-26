
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
=======
# Stage 1: Build the application
FROM maven:3.9.9-amazoncorretto-23 AS build
WORKDIR /app

# Set environment variables
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/glowin
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=admin

COPY pom.xml .
COPY src ./src

# Use environment variables in the Maven command
RUN mvn clean package -Dspring.datasource.url=$SPRING_DATASOURCE_URL -Dspring.datasource.username=$SPRING_DATASOURCE_USERNAME -Dspring.datasource.password=$SPRING_DATASOURCE_PASSWORD

# Stage 2: Run the application
FROM openjdk:23 AS run
WORKDIR /app
COPY --from=build /app/target/glowin-0.0.1-SNAPSHOT.jar /app/glowin.jar
EXPOSE 8080
CMD ["java", "-jar", "glowin.jar"]
