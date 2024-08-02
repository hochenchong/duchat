package hochenchong.duchat.common.chat.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 消息表
 * </p>
 *
 * @author hochenchong
 * @since 2024-08-02
 */
@Data
@Builder
@TableName("t_message")
@Schema(name = "Message", description = "消息表")
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "会话表id")
    @TableField("room_id")
    private Long roomId;

    @Schema(description = "消息发送者uid")
    @TableField("from_uid")
    private Long fromUid;

    @Schema(description = "消息内容")
    @TableField("content")
    private String content;

    @Schema(description = "回复的消息内容")
    @TableField("reply_msg_id")
    private Long replyMsgId;

    @Schema(description = "消息状态 0正常 1删除")
    @TableField("status")
    private Integer status;

    @Schema(description = "与回复的消息间隔多少条")
    @TableField("gap_count")
    private Integer gapCount;

    @Schema(description = "消息类型")
    @TableField("type")
    private Integer type;

    @Schema(description = "扩展信息")
    @TableField("extra")
    private String extra;

    @Schema(description = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
