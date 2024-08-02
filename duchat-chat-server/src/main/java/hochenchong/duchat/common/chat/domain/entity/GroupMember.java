package hochenchong.duchat.common.chat.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 群成员表
 * </p>
 *
 * @author hochenchong
 * @since 2024-08-02
 */
@Getter
@Setter
@TableName("t_group_member")
@Schema(name = "GroupMember", description = "群成员表")
public class GroupMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "群主id")
    @TableField("group_id")
    private Long groupId;

    @Schema(description = "成员uid")
    @TableField("uid")
    private Long uid;

    @Schema(description = "成员角色 1群主 2管理员 3普通成员")
    @TableField("role")
    private Integer role;

    @Schema(description = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
