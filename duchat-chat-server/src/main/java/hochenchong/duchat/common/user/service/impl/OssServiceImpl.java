package hochenchong.duchat.common.user.service.impl;

import hochenchong.duchat.common.common.exception.CustomErrorEnum;
import hochenchong.duchat.common.common.utils.AssertUtils;
import hochenchong.duchat.common.user.domain.enums.OssSceneEnum;
import hochenchong.duchat.common.user.domain.vo.req.oss.UploadUrlReq;
import hochenchong.duchat.common.user.service.OssService;
import hochenchong.duchat.oss.domian.OssReq;
import hochenchong.duchat.oss.domian.OssResp;
import hochenchong.duchat.oss.minio.MinioTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * OSS 服务
 *
 * @author hochenchong
 * @date 2024/08/07
 */
@Service
public class OssServiceImpl implements OssService {
    @Autowired
    private MinioTemplate minioTemplate;

    @Override
    public OssResp getUploadUrl(Long uid, UploadUrlReq req) {
        OssSceneEnum sceneEnum = OssSceneEnum.of(req.getScene());
        AssertUtils.isNotEmpty(sceneEnum, CustomErrorEnum.PARAM_INVALID);
        OssReq ossReq = OssReq.builder()
                .fileName(req.getFileName())
                .filePath(sceneEnum.getPath())
                .uid(uid)
                .autoPath(true)
                .build();
        return minioTemplate.getPreSignedObjectUrl(ossReq);
    }
}
