package org.rxvlvxr.tasks.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@OpenAPIDefinition(
        info = @Info(
                title = "Task Management System API",
                version = "v1.0",
                description = "This API allows users to interact with the task management system, including tasks, users, and authentication.",
                contact = @Contact(
                        name = "Raul Gabaraev",
                        email = "raul.gabaraev.99@gmail.com"
                )
        )
)
public class OpenAPI30Configuration {
}