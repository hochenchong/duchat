package hochenchong.duchat.common.user.domain.vo.req.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WearingBadgeReq {

    @Schema(description = "徽章id")
    @NotNull
    private int badgeId;

}