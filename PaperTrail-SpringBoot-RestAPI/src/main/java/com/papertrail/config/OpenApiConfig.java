package com.papertrail.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Dhiraj",
                        email = "papaertrail.bookshare@gmail.com",
                        url = "https://www.linkedin.com/in/dhiraj-birajdar-pune/"
                ),
                description = "OpenApi documentation for PaperTrail BookShare API",
                title = "PaperTrail BookShare OpenAPI specification",
                version = "1.0.0",
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"
                ),
                termsOfService = "https://www.papertrailbookshare.com/terms" // todo: add terms of service after deployment
        ),
        servers = {
                @Server(
                        description = "Local server",
                        url = "http://localhost:8080/api/v1"
                ),
                @Server(
                        description = "Production server",
                        url = "https://www.papertrailbookshare.com" // todo: add production server url after deployment
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
        description = "Bearer token authentication",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
