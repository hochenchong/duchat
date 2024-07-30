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
 * 黑名单
 * </p>
 *
 * @author hochenchong
 * @since 2024-07-30
 */
@Getter
@Setter
@TableName("t_black")
@Schema(name = "Black 对象", description = "黑名单")
public class Black implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 拉黑目标类型 1.ip 2uid
     *
     * @see hochenchong.duchat.common.user.domain.enums.BlackTypeEnum
     */
    @Schema(description = "拉黑目标类型 1.ip 2uid")
    @TableField("type")
    private Integer type;

    @Schema(description = "拉黑目标")
    @TableField("target")
    private String target;

    @Schema(description = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
