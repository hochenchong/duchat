package hochenchong.duchat.common.user.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户联系人表
 * </p>
 *
 * @author hochenchong
 * @since 2024-07-31
 */
@Getter
@Setter
@TableName("t_user_friend")
@Schema(name = "UserFriend", description = "用户联系人表")
public class UserFriend implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "uid")
    @TableField("uid")
    private Long uid;

    @Schema(description = "好友uid")
    @TableField("friend_uid")
    private Long friendUid;

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
