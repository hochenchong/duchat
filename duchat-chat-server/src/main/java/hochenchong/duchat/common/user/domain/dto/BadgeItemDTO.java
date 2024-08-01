package hochenchong.duchat.common.user.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 徽章道具信息
 *
 * @author hochenchong
 * @date 2024/08/01
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "徽章信息")
public class BadgeItemDTO {
    @Schema(description = "徽章id")
    private Integer itemId;

    @Schema(description = "徽章图标")
    private String img;

    @Schema(description = "徽章描述")
    private String itemDesc;
}
