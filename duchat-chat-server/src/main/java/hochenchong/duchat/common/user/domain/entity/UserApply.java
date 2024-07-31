package hochenchong.duchat.common.user.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户申请表
 * </p>
 *
 * @author hochenchong
 * @since 2024-07-31
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_user_apply")
@Schema(name = "UserApply", description = "用户申请表")
public class UserApply implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "申请人uid")
    @TableField("uid")
    private Long uid;

    @Schema(description = "申请类型 1加好友")
    @TableField("type")
    private Integer type;

    @Schema(description = "接收人uid")
    @TableField("target_id")
    private Long targetId;

    @Schema(description = "申请信息")
    @TableField("msg")
    private String msg;

    @Schema(description = "申请状态 1待审批 2同意")
    @TableField("status")
    private Integer status;

    @Schema(description = "阅读状态 1未读 2已读")
    @TableField("read_status")
    private Integer readStatus;

    @Schema(description = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
