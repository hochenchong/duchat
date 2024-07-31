package hochenchong.duchat.common.user.domain.vo.resp.friend;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 好友申请
 *
 * @author hochenchong
 * @date 2024/07/31
 */
@Data
@Builder
@Schema(description = "好友申请回包")
public class FriendApplyResp {
    @Schema(description = "申请id")
    private Long applyId;

    @Schema(description = "申请用户 uid")
    private Long uid;

    @Schema(description = "申请类型 1加好友")
    private int type;

    @Schema(description = "申请信息")
    private String msg;

    @Schema(description = "申请状态 1待审批 2同意")
    private int status;
}
