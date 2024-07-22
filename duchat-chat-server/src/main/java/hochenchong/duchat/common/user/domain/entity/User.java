package hochenchong.duchat.common.user.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author hochenchong
 * @since 2024-03-19
 */
@Getter
@Setter
@TableName("t_user")
@ApiModel(value = "User对象", description = "用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户昵称")
    @TableField("name")
    private String name;

    @ApiModelProperty("用户密码")
    @TableField("password")
    private String password;

    @ApiModelProperty("用户头像")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty("性别 1为男性，2为女性")
    @TableField("sex")
    private Integer sex;

    @ApiModelProperty("微信openid用户标识")
    @TableField("open_id")
    private String openId;

    @ApiModelProperty("在线状态 1在线 2离线")
    @TableField("active_status")
    private Integer activeStatus;

    @ApiModelProperty("最后上下线时间")
    @TableField("last_opt_time")
    private LocalDateTime lastOptTime;

    @ApiModelProperty("ip信息")
    @TableField("ip_info")
    private String ipInfo;

    @ApiModelProperty("佩戴的徽章id")
    @TableField("item_id")
    private Long itemId;

    @ApiModelProperty("使用状态 0.正常 1拉黑")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
