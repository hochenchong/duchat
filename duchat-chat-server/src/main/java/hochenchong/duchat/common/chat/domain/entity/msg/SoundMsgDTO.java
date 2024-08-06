package hochenchong.duchat.common.chat.domain.entity.msg;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * 语音类型消息
 *
 * @author hochenchong
 * @date 2024/08/02
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "语音类型消息")
public class SoundMsgDTO extends BaseFileDTO {
    @Schema(description = "语音时长，单位：秒")
    @NotNull
    private Integer second;
}
