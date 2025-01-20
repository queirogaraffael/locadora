package com.unifacisa.locadora.configs;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ConditionalOnExpression("${spring.profiles.active} == 'dev'")
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Locadora API")
                        .description("O projeto \"locadora\" é uma API para gerenciamento de filmes na locadora. Ele " +
                                "utiliza Redis para caching e foi desenvolvido em Java 17 com Spring Boot. " +
                                "As dependências incluem Spring Data JPA, Spring Web, Lombok, Postgresql, " +
                                "Springdoc OpenAPI, Redis e Caching. O projeto é gerenciado com Maven.")
                        .version("0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentação da API da Locadora"));
    }


}