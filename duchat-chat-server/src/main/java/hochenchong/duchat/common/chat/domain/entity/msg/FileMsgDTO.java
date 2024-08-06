package hochenchong.duchat.common.chat.domain.entity.msg;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * 图片类型消息
 *
 * @author hochenchong
 * @date 2024/08/02
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "文件类型消息")
public class FileMsgDTO extends BaseFileDTO {
    @Schema(description = "文件名称")
    @NotBlank
    private String fileName;
}
