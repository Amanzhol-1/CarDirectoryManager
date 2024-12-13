package spring.cardirectorymanager.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Car Directory Manager API")
                        .version("1.0")
                        .description("API документация для управления каталогом автомобилей с аутентификацией на основе JWT."));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("car-directory")
                .pathsToMatch("/auth/**", "/cars/**")
                .build();
    }
}
