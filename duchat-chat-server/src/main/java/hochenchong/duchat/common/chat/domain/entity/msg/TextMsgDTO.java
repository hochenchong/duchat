package hochenchong.duchat.common.chat.domain.entity.msg;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文本类型消息
 *
 * @author hochenchong
 * @date 2024/09/02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "文本类型消息")
public class TextMsgDTO {
    @Schema(description = "消息内容")
    @NotNull
    private String content;

    @Schema(description = "回复消息 id")
    private Long replyMsgId;

    @Schema(description = "与回复消息间隔条数")
    private int gapCount;
}
