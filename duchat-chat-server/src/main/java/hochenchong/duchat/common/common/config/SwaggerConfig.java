package hochenchong.duchat.common.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger 配置信息
 *
 * @author hochenchong
 * @date 2024/07/23
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                        // 网站标题
                        .title("嘟嘟聊天室")
                        .contact(new Contact().name("HochenChong").url("https://github.com/hochenchong"))
                        // 版本号
                        .version("1.0")
                        .description("欢迎来到嘟嘟聊天室")
                        .termsOfService("https://github.com/hochenchong")
                // .license(new License().name("Apache 2.0").url("https://github.com/hochenchong"))
        );
    }
}