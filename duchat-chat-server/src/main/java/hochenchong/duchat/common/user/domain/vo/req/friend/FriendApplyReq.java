package hochenchong.duchat.common.user.domain.vo.req.friend;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 好友申请请求
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendApplyReq {

    @Schema(description = "申请好友uid")
    @NotNull
    private Long uid;

    @Schema(description = "申请信息")
    @NotBlank
    private String msg;

}