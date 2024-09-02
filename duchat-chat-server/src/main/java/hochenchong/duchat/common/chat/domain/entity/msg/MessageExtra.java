package hochenchong.duchat.common.chat.domain.entity.msg;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息扩展信息
 *
 * @author hochenchong
 * @date 2024/08/02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "消息扩展信息")
public class MessageExtra {
    @Schema(description = "文本消息")
    private TextMsgDTO textMsgDTO;
    @Schema(description = "图片消息")
    private ImgMsgDTO imgMsgDTO;
    @Schema(description = "语音消息")
    private SoundMsgDTO soundMsgDTO;
    @Schema(description = "视频消息")
    private VideoMsgDTO videoMsgDTO;
    @Schema(description = "文件消息")
    private FileMsgDTO fileMsg;
}
