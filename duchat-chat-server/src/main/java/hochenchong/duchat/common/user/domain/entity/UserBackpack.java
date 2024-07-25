package hochenchong.duchat.common.user.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户背包表
 * </p>
 *
 * @author hochenchong
 * @since 2024-07-24
 */
@Getter
@Setter
@TableName("t_user_backpack")
@Schema(name = "UserBackpack对象", description = "用户背包表")
public class UserBackpack implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "uid")
    @TableField("uid")
    private Long uid;

    @Schema(description = "道具 id")
    @TableField("item_id")
    private int itemId;

    @Schema(description = "使用状态 0.待使用 1已使用")
    @TableField("status")
    private int status;

    @Schema(description = "幂等号")
    @TableField("idempotent")
    private String idempotent;

    @Schema(description = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
