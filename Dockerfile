# Stage 1: Build the application
FROM maven:3.9.9 AS build
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