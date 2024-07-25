package hochenchong.duchat.common.user.domain.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户信息回包
 *
 * @author hochenchong
 * @date 2024/07/24
 */
@Data
@Schema(description = "用户详情")
public class UserInfoResp {
    @Schema(description = "用户id")
    private Long id;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "改名卡次数")
    private int modifyNameChance;
}
