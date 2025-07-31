package com.example.suntcamp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Suntcamp",
                version = "v1",
                description = "선트캠프 프로젝트"
        )
)
public class OpenApiConfig {
}
