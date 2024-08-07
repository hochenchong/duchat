package hochenchong.duchat.oss.minio;

import cn.hutool.core.io.file.FileNameUtil;
import hochenchong.duchat.oss.OssProperties;
import hochenchong.duchat.oss.constant.StrConstant;
import hochenchong.duchat.oss.domian.OssReq;
import hochenchong.duchat.oss.domian.OssResp;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

/**
 * @author hochenchong
 * @date 2024/08/07
 */
@AllArgsConstructor
public class MinioTemplate {

    /**
     * Minio 客户端
     */
    MinioClient minioClient;

    /**
     * Minio 配置类
     */
    OssProperties ossProperties;

    /**
     * 获取带签名的，过期事件一天，put 请求的 上传路径；以及上传成功后的下载路径
     *
     * @param req 请求
     * @return 信息
     */
    @SneakyThrows
    public OssResp getPreSignedObjectUrl(OssReq req) {
        String absolutePath = req.isAutoPath() ? generateAutoPath(req) : req.getFilePath() + StrConstant.SLASH + req.getFileName();
        String url = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.PUT)
                        .bucket(ossProperties.getBucketName())
                        .object(absolutePath)
                        .expiry(60 * 60 * 24)
                        .build());
        return OssResp.builder()
                .uploadUrl(url)
                .downloadUrl(getDownloadUrl(ossProperties.getBucketName(), absolutePath))
                .build();
    }

    /**
     * 生成随机文件名
     *
     * @return 随机文件名
     */
    public String generateAutoPath(OssReq req) {
        String uid = Optional.ofNullable(req.getUid()).map(String::valueOf).orElse("000000");
        UUID uuid = UUID.randomUUID();
        String suffix = FileNameUtil.getSuffix(req.getFileName());
        LocalDate now = LocalDate.now();
        return req.getFilePath() + StrConstant.SLASH + now.getYear() + StrConstant.SLASH + now.getMonthValue() + StrConstant.SLASH + uid + StrConstant.SLASH + uuid + StrConstant.DOT + suffix;
    }

    private String getDownloadUrl(String bucket, String pathFile) {
        return ossProperties.getEndPoint() + StrConstant.SLASH + bucket + StrConstant.SLASH + pathFile;
    }
}
