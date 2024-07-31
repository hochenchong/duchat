package hochenchong.duchat.common.user.domain.vo.req.friend;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 好友删除
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendDeleteReq {

    @Schema(description = "删除的好友 uid")
    @NotNull
    private Long uid;

}