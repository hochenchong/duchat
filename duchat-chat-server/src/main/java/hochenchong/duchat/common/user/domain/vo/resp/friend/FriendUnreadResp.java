package hochenchong.duchat.common.user.domain.vo.resp.friend;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hochenchong
 * @date 2024/07/31
 */
@Data
@Builder
@Schema(description = "申请好友未读数")
@AllArgsConstructor
@NoArgsConstructor
public class FriendUnreadResp {

    @Schema(description = "申请好友未读数")
    private int unReadCount;
}
