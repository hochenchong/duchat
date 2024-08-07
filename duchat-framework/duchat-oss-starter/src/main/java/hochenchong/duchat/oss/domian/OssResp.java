package hochenchong.duchat.oss.domian;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 上传 URL 请求回包
 *
 * @author hochenchong
 * @date 2024/08/07
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OssResp {
    @Schema(description = "上传的临时 url")
    private String uploadUrl;

    @Schema(description = "上传成功后下载的 url")
    private String downloadUrl;
}
