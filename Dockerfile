# Stage 1: Build the application
FROM maven:3.9.9-amazoncorretto-23 AS build
WORKDIR /app

# Define build arguments
ARG SPRING_MAIL_HOST
ARG SPRING_MAIL_PORT
ARG SPRING_MAIL_USERNAME
ARG SPRING_MAIL_PASSWORD
ARG SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH
ARG SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE
ARG SPRING_MAIL_PROPERTIES_MAIL_SMTP_SSL_TRUST
ARG SPRING_MAIL_PROPERTIES_MAIL_SMTP_LOCALHOST
ARG jwt_expiracion
ARG jwt_secreto

# Set environment variables using build arguments
ENV SPRING_MAIL_HOST=${SPRING_MAIL_HOST}
ENV SPRING_MAIL_PORT=${SPRING_MAIL_PORT}
ENV SPRING_MAIL_USERNAME=${SPRING_MAIL_USERNAME}
ENV SPRING_MAIL_PASSWORD=${SPRING_MAIL_PASSWORD}
ENV SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH=${SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH}
ENV SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE=${SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE}
ENV SPRING_MAIL_PROPERTIES_MAIL_SMTP_SSL_TRUST=${SPRING_MAIL_PROPERTIES_MAIL_SMTP_SSL_TRUST}
ENV SPRING_MAIL_PROPERTIES_MAIL_SMTP_LOCALHOST=${SPRING_MAIL_PROPERTIES_MAIL_SMTP_LOCALHOST}
ENV jwt_expiracion=${jwt_expiracion}
ENV jwt_secreto=${jwt_secreto}

# Print environment variables to console
RUN echo "SPRING_MAIL_HOST=${SPRING_MAIL_HOST}" && \
    echo "SPRING_MAIL_PORT=${SPRING_MAIL_PORT}" && \
    echo "SPRING_MAIL_USERNAME=${SPRING_MAIL_USERNAME}" && \
    echo "SPRING_MAIL_PASSWORD=${SPRING_MAIL_PASSWORD}" && \
    echo "SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH=${SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH}" && \
    echo "SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE=${SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE}" && \
    echo "SPRING_MAIL_PROPERTIES_MAIL_SMTP_SSL_TRUST=${SPRING_MAIL_PROPERTIES_MAIL_SMTP_SSL_TRUST}" && \
    echo "SPRING_MAIL_PROPERTIES_MAIL_SMTP_LOCALHOST=${SPRING_MAIL_PROPERTIES_MAIL_SMTP_LOCALHOST}" && \
    echo "jwt_expiracion=${jwt_expiracion}" && \
    echo "jwt_secreto=${jwt_secreto}"

COPY pom.xml .
COPY src ./src

# Use environment variables in the Maven command
RUN mvn clean package -DskipTests -Dspring.datasource.url=$SPRING_DATASOURCE_URL -Dspring.datasource.username=$SPRING_DATASOURCE_USERNAME -Dspring.datasource.password=$SPRING_DATASOURCE_PASSWORD

# Stage 2: Run the application
FROM openjdk:23 AS run
WORKDIR /app
COPY --from=build /app/target/glowin-0.0.1-SNAPSHOT.jar /app/glowin.jar

# Define build arguments again to pass them to this stage
ARG SPRING_MAIL_HOST
ARG SPRING_MAIL_PORT
ARG SPRING_MAIL_USERNAME
ARG SPRING_MAIL_PASSWORD
ARG SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH
ARG SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE
ARG SPRING_MAIL_PROPERTIES_MAIL_SMTP_SSL_TRUST
ARG SPRING_MAIL_PROPERTIES_MAIL_SMTP_LOCALHOST
ARG jwt_expiracion
ARG jwt_secreto

# Set environment variables again to ensure they are available in the running container
ENV SPRING_MAIL_HOST=${SPRING_MAIL_HOST}
ENV SPRING_MAIL_PORT=${SPRING_MAIL_PORT}
ENV SPRING_MAIL_USERNAME=${SPRING_MAIL_USERNAME}
ENV SPRING_MAIL_PASSWORD=${SPRING_MAIL_PASSWORD}
ENV SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH=${SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH}
ENV SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE=${SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE}
ENV SPRING_MAIL_PROPERTIES_MAIL_SMTP_SSL_TRUST=${SPRING_MAIL_PROPERTIES_MAIL_SMTP_SSL_TRUST}
ENV SPRING_MAIL_PROPERTIES_MAIL_SMTP_LOCALHOST=${SPRING_MAIL_PROPERTIES_MAIL_SMTP_LOCALHOST}
ENV jwt_expiracion=${jwt_expiracion}
ENV jwt_secreto=${jwt_secreto}

# Print environment variables to console
RUN echo "SPRING_MAIL_HOST=${SPRING_MAIL_HOST}" && \
    echo "SPRING_MAIL_PORT=${SPRING_MAIL_PORT}" && \
    echo "SPRING_MAIL_USERNAME=${SPRING_MAIL_USERNAME}" && \
    echo "SPRING_MAIL_PASSWORD=${SPRING_MAIL_PASSWORD}" && \
    echo "SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH=${SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH}" && \
    echo "SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE=${SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE}" && \
    echo "SPRING_MAIL_PROPERTIES_MAIL_SMTP_SSL_TRUST=${SPRING_MAIL_PROPERTIES_MAIL_SMTP_SSL_TRUST}" && \
    echo "SPRING_MAIL_PROPERTIES_MAIL_SMTP_LOCALHOST=${SPRING_MAIL_PROPERTIES_MAIL_SMTP_LOCALHOST}" && \
    echo "jwt_expiracion=${jwt_expiracion}" && \
    echo "jwt_secreto=${jwt_secreto}"

EXPOSE 8080
CMD ["java", "-jar", "glowin.jar"]