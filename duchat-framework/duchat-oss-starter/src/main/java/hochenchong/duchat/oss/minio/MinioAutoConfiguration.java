package hochenchong.duchat.oss.minio;

import hochenchong.duchat.oss.OssProperties;
import io.minio.MinioClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Minio 自动配置
 *
 * @author hochenchong
 * @date 2024/08/07
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(OssProperties.class)
@ConditionalOnExpression("${oss.enabled}")
@ConditionalOnProperty(value = "oss.type", havingValue = "minio")
public class MinioAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(MinioClient.class)
    public MinioClient minioClient(OssProperties ossProperties) {
        return MinioClient.builder()
                .endpoint(ossProperties.getEndPoint())
                .credentials(ossProperties.getAccessKey(), ossProperties.getSecretKey())
                .build();
    }

    @Bean
    @ConditionalOnBean({MinioClient.class})
    @ConditionalOnMissingBean(MinioTemplate.class)
    public MinioTemplate minioTemplate(MinioClient minioClient, OssProperties ossProperties) {
        return new MinioTemplate(minioClient, ossProperties);
    }
}
