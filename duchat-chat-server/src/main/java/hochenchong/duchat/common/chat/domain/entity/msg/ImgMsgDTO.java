package hochenchong.duchat.common.chat.domain.entity.msg;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
@Schema(description = "图片类型消息")
public class ImgMsgDTO extends BaseFileDTO {
    @Schema(description = "宽度")
    @NotNull
    private Integer width;

    @Schema(description = "高度")
    @NotNull
    private Integer height;
}
