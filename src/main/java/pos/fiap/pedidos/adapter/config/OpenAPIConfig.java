package pos.fiap.lanchonete.adapter.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        var info = new Info()
                .title("Lanchonete API")
                .version("1.0")
                .description("APIs para exposição de endpoints do serviço referente ao gerenciamento de pedidos.");

        return new OpenAPI().info(info);
    }
}
