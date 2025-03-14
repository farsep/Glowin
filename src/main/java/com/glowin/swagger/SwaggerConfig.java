package com.glowin.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info().title("My REST API")
                        .description("""
                            Esta API requiere autenticación utilizando tokens JWT. Los siguientes endpoints están disponibles:
                    
                            - Endpoints Públicos:
                              - `/swagger-ui.html`, `/v3/api-docs`, `/swagger-ui/**`, `/v3/api-docs/**`: UI de Swagger y documentación de la API.
                              - `POST /auth/**`: Endpoints de autenticación.
                              - `GET /imagenes-servicios/**`: Acceso a imágenes de servicios.
                              - `POST /usuarios/**`: Registro de usuarios.
                              - `GET /categorias-servicios/all`: Listado de todas las categorías de servicios.
                              - `GET /servicios/all`: Listado de todos los servicios.
                    
                            - Endpoints Protegidos:
                              - `/servicios/**`, `/empleados/**`, `/categorias-servicios/**`: Accesible por usuarios con roles `SUPER_ADMINISTRADOR` o `ADMINISTRADOR`.
                              - `GET /usuarios/**`, `PUT /usuarios/**`, `DELETE /usuarios/**`: Accesible por usuarios con roles `SUPER_ADMINISTRADOR`, `ADMINISTRADOR` o `CLIENTE`.
                    
                            Todos los demás endpoints requieren autenticación.
                    
                            Al iniciar sesión, la respuesta incluirá un token JWT que debe ser incluido en el encabezado Authorization de las solicitudes subsecuentes.
                    
                            Para pasar el token como un token Bearer en el encabezado Authorization, inclúyelo de la siguiente manera:
                            - **Nombre del Encabezado**: `Authorization`
                            - **Valor del Encabezado**: `Bearer your_jwt_token_here`
                            """)
                        .version("JWT Token Implemented")
                        .contact(new Contact().name("Zaramambiches Team")
                                .email("wazateam@gmail.com")
                                .url("www.zaramambiches.com"))
                        .license(new License().name("License of API")
                                .url("API license URL")));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }
}