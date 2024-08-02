package hochenchong.duchat.common.chat.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 会话列表
 * </p>
 *
 * @author hochenchong
 * @since 2024-08-02
 */
@Getter
@Setter
@TableName("t_contact")
@Schema(name = "Contact", description = "会话列表")
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "uid")
    @TableField("uid")
    private Long uid;

    @Schema(description = "房间id")
    @TableField("room_id")
    private Long roomId;

    @Schema(description = "阅读到的时间")
    @TableField("read_time")
    private LocalDateTime readTime;

    @Schema(description = "会话内消息最后更新的时间")
    @TableField("active_time")
    private LocalDateTime activeTime;

    @Schema(description = "会话最新消息id")
    @TableField("last_msg_id")
    private Long lastMsgId;

    @Schema(description = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
