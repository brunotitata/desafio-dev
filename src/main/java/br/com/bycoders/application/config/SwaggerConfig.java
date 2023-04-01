package br.com.bycoders.application.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("Desafio ByCoders_ - Documentação do desafio de processamento de arquivos CNAB e leitura dos dados")
                .packagesToScan("br.com.bycoders.port.adapters.controller")
                .build();
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Desafio ByCoders_ - Documentação do desafio de processamento de arquivos CNAB e leitura dos dados")
                        .description("Para acessar a pagina de upload de arquivo CNAB, acesse: http://localhost:8080/upload")
                        .contact(new Contact()
                                .name("Bruno Costa")
                                .email("brunotitata@gmail.com"))
                        .version("1.0.0"));
    }
}