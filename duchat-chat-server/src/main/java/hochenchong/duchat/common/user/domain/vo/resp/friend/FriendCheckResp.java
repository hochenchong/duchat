package hochenchong.duchat.common.user.domain.vo.resp.friend;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 好友校验
 *
 * @author hochenchong
 * @date 2024/07/31
 */
@Data
@Builder
@Schema(description = "好友校验")
@AllArgsConstructor
@NoArgsConstructor
public class FriendCheckResp {
    @Schema(description = "校验结果")
    private List<FriendCheck> checks;

    @Data
    public static class FriendCheck {
        @Schema(description = "校验的用户 id")
        private Long uid;

        @Schema(description = "是否为好友")
        private Boolean isFriend;
    }
}
