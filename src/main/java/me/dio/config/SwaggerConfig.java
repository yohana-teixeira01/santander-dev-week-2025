package me.dio.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customAPI(){
        // Aqui adicionei o titilo para minha API, mas poderia adicionar a versão, lincença e uma URL da licença.
        return new OpenAPI().info(new Info().title("API do Banco Santander "));
    }
}
