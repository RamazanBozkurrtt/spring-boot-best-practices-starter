package org.project.bestpractice.configuration.swagger;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Best Practice API")
                        .version("1.0")
                        .description("Bu proje Spring Boot Best Practices (AOP, Exception Handling, Layered Arch) kullanılarak geliştirilmiştir.")
                        .contact(new Contact()
                                .name("Ramazan Bozkurt")
                                .email("ramazannbozkurrtt@outlook.com")));
    }

}
