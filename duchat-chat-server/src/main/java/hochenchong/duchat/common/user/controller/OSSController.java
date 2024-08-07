package hochenchong.duchat.common.user.controller;

import hochenchong.duchat.common.common.domain.vo.response.ApiResult;
import hochenchong.duchat.common.common.utils.RequestHolder;
import hochenchong.duchat.common.user.domain.vo.req.oss.UploadUrlReq;
import hochenchong.duchat.common.user.service.OssService;
import hochenchong.duchat.oss.domian.OssResp;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * OSS 控制器
 *
 * @author hochenchong
 * @date 2024/08/07
 */
@RestController
@RequestMapping("/capi/oss")
public class OSSController {
    @Autowired
    private OssService ossService;

    @GetMapping("/upload/url")
    @Schema(description = "获取临时上传链接")
    public ApiResult<OssResp> getUploadUrl(@Valid UploadUrlReq req) {
        return ApiResult.success(ossService.getUploadUrl(RequestHolder.get().getUid(), req));
    }
}
