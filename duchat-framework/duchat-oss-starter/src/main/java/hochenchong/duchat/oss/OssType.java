package hochenchong.duchat.oss;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * OSS 类型
 *
 * @author hochenchong
 * @date 2024/08/07
 */
@Getter
@AllArgsConstructor
public enum OssType {
    AWS(1, "亚马逊S3"),

    ALIYUN(2, "阿里云"),

    TENCENT(3, "腾讯云"),

    MINIO(4, "minio"),
    ;

    private final int type;
    private final String name;
}
