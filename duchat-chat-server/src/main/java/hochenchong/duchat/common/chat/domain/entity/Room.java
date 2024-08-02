package hochenchong.duchat.common.chat.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import hochenchong.duchat.common.chat.domain.enums.RoomTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 房间表
 * </p>
 *
 * @author hochenchong
 * @since 2024-08-02
 */
@Getter
@Setter
@TableName("t_room")
@Schema(name = "Room", description = "房间表")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Room implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "房间类型 1群聊 2单聊")
    @TableField("type")
    private Integer type;

    @Schema(description = "会话中的最后一条消息id")
    @TableField("last_msg_id")
    private Long lastMsgId;

    @Schema(description = "额外信息（根据不同类型房间有不同存储的东西）")
    @TableField("ext_json")
    private String extJson;

    @Schema(description = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @JsonIgnore
    public boolean isRoomFriend() {
        return RoomTypeEnum.of(this.type) == RoomTypeEnum.FRIEND;
    }

    @JsonIgnore
    public boolean isRoomGroup() {
        return RoomTypeEnum.of(this.type) == RoomTypeEnum.GROUP;
    }
}
