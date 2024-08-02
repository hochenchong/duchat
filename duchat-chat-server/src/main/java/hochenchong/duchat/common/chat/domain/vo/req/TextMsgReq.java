package hochenchong.duchat.common.chat.domain.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文本类型消息请求
 *
 * @author hochenchong
 * @date 2024/08/02
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "文本类型消息请求")
public class TextMsgReq {
    @NotBlank
    @Size(max = 1024, message = "消息内容过长")
    @Schema(description = "消息内容")
    private String text;
}
