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
                        .description("This API requires authentication using JWT tokens. " +
                                "Users with roles SUPER_ADMINISTRADOR or ADMINISTRADOR can access " +
                                "the endpoints in controller-usuarios, controller-servicios, " +
                                "controller-empleados, and controller-categorias-servicios. \n" +
                                "The Usuarios POST endpoint is public. \n" +
                                "Client TOKEN just can manipulate their Reservas, and edit their profile.\n\n\n" +
                                "When Login, the response will include a JWT token that must be included in the " +
                                "Authorization header of subsequent requests. \n\n\n"
                                )
                        .version("JWT Token Implemented")
                        .contact(new Contact().name("Zaramambiches Team")
                                .email("salloszraj@gmail.com")
                                .url("www.baeldung.com"))
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