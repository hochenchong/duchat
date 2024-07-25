package hochenchong.duchat.common.user.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 道具配置表
 * </p>
 *
 * @author hochenchong
 * @since 2024-07-24
 */
@Getter
@Setter
@TableName("t_item_config")
@Schema(name = "ItemConfig 对象", description = "道具配置表")
public class ItemConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId("id")
    private Integer id;

    @Schema(description = "道具类型 1改名卡 2徽章")
    @TableField("type")
    private Integer type;

    @Schema(description = "道具图片")
    @TableField("img")
    private String img;

    @Schema(description = "道具功能描述")
    @TableField("describe")
    private String describe;

    @Schema(description = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
