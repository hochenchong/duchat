package hochenchong.duchat.common.chat.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 单聊房间表
 * </p>
 *
 * @author hochenchong
 * @since 2024-08-02
 */
@Getter
@Setter
@TableName("t_room_friend")
@Schema(name = "RoomFriend", description = "单聊房间表")
public class RoomFriend implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "房间id")
    @TableField("room_id")
    private Long roomId;

    @Schema(description = "uid1（更小的uid）")
    @TableField("uid1")
    private Long uid1;

    @Schema(description = "uid2（更大的uid）")
    @TableField("uid2")
    private Long uid2;

    @Schema(description = "房间 key 由两个 uid 拼接，先做排序 uid1_uid2")
    @TableField("room_key")
    private String roomKey;

    @Schema(description = "房间状态 0正常 1禁用")
    @TableField("delete_status")
    private Integer deleteStatus;

    @Schema(description = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
