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
 * 群聊房间表
 * </p>
 *
 * @author hochenchong
 * @since 2024-08-02
 */
@Getter
@Setter
@TableName("t_room_group")
@Schema(name = "RoomGroup", description = "群聊房间表")
public class RoomGroup implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "房间id")
    @TableField("room_id")
    private Long roomId;

    @Schema(description = "群名称")
    @TableField("name")
    private String name;

    @Schema(description = "群头像")
    @TableField("avatar")
    private String avatar;

    @Schema(description = "额外信息（根据不同类型房间有不同存储的东西）")
    @TableField("ext_json")
    private String extJson;

    @Schema(description = "逻辑删除(0-正常,1-删除)")
    @TableField("delete_status")
    private Integer deleteStatus;

    @Schema(description = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
