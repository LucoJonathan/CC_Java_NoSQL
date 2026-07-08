package com.jonathanluco.doctorapp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration OpenAPI/Swagger de l'API.
 */
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Medical Doctor App API")
                        .version("1.0.0")
                        .description("API REST pour la gestion des consultations médicales, patients, médecins et médicaments en NoSQL (MongoDB)")
                        .contact(new Contact()
                                .name("Jonathan Luco")
                                .url("https://github.com/LucoJonathan"))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT")));
    }
}
