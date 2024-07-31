package hochenchong.duchat.common.user.domain.vo.req.friend;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 批量好友校验
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendCheckReq {

    @Schema(description = "校验好友的 uid")
    @NotEmpty
    @Size(max = 50)
    private List<Long> ids;
}