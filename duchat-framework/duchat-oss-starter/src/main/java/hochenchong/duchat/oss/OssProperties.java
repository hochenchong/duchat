package hochenchong.duchat.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * OSS 配置
 *     适配 S3
 *
 * @author hochenchong
 * @date 2024/08/07
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "oss")
public class OssProperties {
    /**
     * 是否开启
     */
    private boolean enabled;
    /**
     * OSS 类型
     */
    private OssType type;
    /**
     * OSS 访问 URL
     */
    private String endPoint;
    /**
     * 存储桶
     */
    private String bucketName;
    /**
     * 区域
     */
    private String region;
    /**
     * 访问密钥
     */
    private String accessKey;
    /**
     * 密钥
     */
    private String secretKey;

}
