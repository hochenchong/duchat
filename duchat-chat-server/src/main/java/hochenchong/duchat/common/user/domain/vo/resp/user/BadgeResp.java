package hochenchong.duchat.common.user.domain.vo.resp.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户信息回包
 *
 * @author hochenchong
 * @date 2024/07/24
 */
@Data
@Schema(description = "徽章回包")
public class BadgeResp {
    @Schema(description = "徽章id")
    private int id;

    @Schema(description = "徽章图标")
    private String img;

    @Schema(description = "徽章描述")
    private String desc;

    @Schema(description = "是否拥有 0否 1是")
    private int ownStatus;

    @Schema(description = "是否佩戴 0否 1是")
    private int wearStatus;
}
