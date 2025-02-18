package com.luca.film.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * Configuration class for OpenAPI documentation.
 * It provides metadata about the API, including contact details, security schemes, and server information.
 */
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Luca",
                        email = "Luca@example.com"
                ),
                description = "OpenAPI documentation for Spring Security",
                title = "OpenAPI Specification - Luca",
                version = "1.0",
                license = @License(
                        name = "License name",
                        url = "https://example-license.com"
                ),
                termsOfService = "Terms of Service"
        ),
        servers = {
                @Server(
                        description = "Local Environment",
                        url = "http://localhost:8080/api/v1"
                ),
                @Server(
                        description = "Production Environment",
                        url = "http://luca.com/film-social-network"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT authentication scheme",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
