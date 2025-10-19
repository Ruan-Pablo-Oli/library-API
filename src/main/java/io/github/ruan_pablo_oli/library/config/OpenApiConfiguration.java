package io.github.ruan_pablo_oli.library.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info= @Info(
                title="Library API",
                version = "v1",
                contact = @Contact(
                        name = "Ruan Pablo Furtado Oliveira",
                        email = "ruanpablooliveira20@gmail.com",
                        url = "libraryapi.com"
                )

        )
)
public class OpenApiConfiguration {

}
