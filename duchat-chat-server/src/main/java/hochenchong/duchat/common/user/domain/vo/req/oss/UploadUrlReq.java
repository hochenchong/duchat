package hochenchong.duchat.common.user.domain.vo.req.oss;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hochenchong
 * @date 2024/08/07
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadUrlReq {
    @Schema(description = "场景：1.聊天室内容类型 2. 表情包")
    @NotNull
    private Integer scene;

    @Schema(description = "文件名（带后缀）")
    @NotBlank
    private String fileName;
}
