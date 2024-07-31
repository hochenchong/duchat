package hochenchong.duchat.common.user.domain.vo.req.friend;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 审批好友申请
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendApproveReq {

    @Schema(description = "申请 Id")
    @NotNull
    private Long applyId;

}