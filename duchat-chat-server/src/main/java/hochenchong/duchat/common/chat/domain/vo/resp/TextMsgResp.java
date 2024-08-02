package hochenchong.duchat.common.chat.domain.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文本类型消息回包
 *
 * @author hochenchong
 * @date 2024/08/02
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "文本类型消息回包")
public class TextMsgResp {
    @Schema(description = "消息内容")
    private String text;
}
