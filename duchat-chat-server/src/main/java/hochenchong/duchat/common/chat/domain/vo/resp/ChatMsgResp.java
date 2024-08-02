package hochenchong.duchat.common.chat.domain.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 消息回包
 *
 * @author hochenchong
 * @date 2024/08/02
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "消息回包")
public class ChatMsgResp {
    @Schema(description = "消息id")
    private Long id;

    @Schema(description = "房间id")
    private Long roomId;

    @Schema(description = "发送用户 id")
    private Long fromUid;

    @Schema(description = "消息类型")
    private Integer type;

    @Schema(description = "消息内容")
    private Object body;

    @Schema(description = "消息创建时间")
    private LocalDateTime createTime;
}
