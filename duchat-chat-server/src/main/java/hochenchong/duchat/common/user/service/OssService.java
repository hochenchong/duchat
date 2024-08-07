package hochenchong.duchat.common.user.service;

import hochenchong.duchat.common.user.domain.vo.req.oss.UploadUrlReq;
import hochenchong.duchat.oss.domian.OssResp;

/**
 * OSS 服务
 *
 * @author hochenchong
 * @date 2024/08/07
 */
public interface OssService {
    /**
     * 获取临时上传路径
     *
     * @param uid 用户id
     * @param req 上传请求
     * @return 临时上传路径
     */
    OssResp getUploadUrl(Long uid, UploadUrlReq req);
}
