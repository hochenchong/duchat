package hochenchong.duchat.common.chat.domain.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息发送请求
 *
 * @author hochenchong
 * @date 2024/08/02
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "消息发送请求")
public class ChatMsgReq {
    @Schema(description = "房间id")
    @NotNull
    private Long roomId;

    @Schema(description = "消息类型")
    @NotNull
    private Integer msgType;

    /**
     * @see hochenchong.duchat.common.chat.domain.entity.msg
     */
    @Schema(description = "消息内容")
    @NotNull
    private Object body;
}
