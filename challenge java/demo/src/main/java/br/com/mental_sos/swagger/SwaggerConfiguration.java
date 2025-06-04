package br.com.mental_sos.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement; 
import io.swagger.v3.oas.models.security.SecurityScheme;     

@Configuration
public class SwaggerConfiguration {

    @Bean
    OpenAPI configurarSwagger() {
        final String securitySchemeName = "bearerAuth"; 

        return new OpenAPI()
                .info(new Info()
                        .title("Projeto de chat interativo para calamidades")
                        .description("Este projeto oferece uma implementação que possibilita "
                                + "que psicólogos possam se conectar com pessoas que sofreram por "
                                + "conta de calamidades climáticas")
                        .summary("Sumário: Este projeto oferece uma implementação que possibilita"
                                + " uma conversa entre psicólogos e vitimas de desastres naturais")
                        .version("v1.0.0")
                        .license(new License().url("https://mentalsos-minhaapi-c2hcaffwbrd4euam.brazilsouth-01.azurewebsites.net/swagger-ui/index.html#/") 
                                .name("Licença - Projeto de chat interativo para calamidades - v1.0.0"))
                        .termsOfService("Termos de Serviço")
                )
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName)
                )
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP) 
                                        .scheme("bearer")               
                                        .bearerFormat("JWT")          
                                        .description("Insira o token JWT no formato: Bearer {token}")
                        )
                );
    }
}