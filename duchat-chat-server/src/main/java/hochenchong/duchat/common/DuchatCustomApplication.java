package hochenchong.duchat.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hochenchong
 * @date 2024/03/15
 */
@SpringBootApplication(scanBasePackages = {"hochenchong.duchat"})
@MapperScan({"hochenchong.duchat.common.**.mapper"})
public class DuchatCustomApplication {
    public static void main(String[] args) {
        SpringApplication.run(DuchatCustomApplication.class, args);
    }
}
