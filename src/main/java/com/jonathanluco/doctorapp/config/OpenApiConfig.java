package com.jonathanluco.doctorapp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration OpenAPI/Swagger pour la documentation de l'API REST.
 * Génère une interface Swagger accessible sur /swagger-ui.html
 */
@Configuration
public class OpenApiConfig {

    /**
     * Configures the OpenAPI documentation with metadata about the API.
     *
     * @return OpenAPI configuration bean
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Medical Doctor App API")
                        .version("1.0.0")
                        .description("API REST pour la gestion des consultations médicales, patients, médecins et médicaments en NoSQL (MongoDB)")
                        .contact(new Contact()
                                .name("Jonathan Luco")
                                .email("jonathan.luco@example.com")
                                .url("https://github.com/LucoJonathan"))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT")));
    }
}
