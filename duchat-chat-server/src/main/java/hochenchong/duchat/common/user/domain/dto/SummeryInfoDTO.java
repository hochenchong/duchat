package hochenchong.duchat.common.user.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息汇总
 * @author hochenchong
 * @date 2024/08/01
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "用户信息汇总")
public class SummeryInfoDTO {
    @Schema(description = "用户id")
    private Long uid;

    @Schema(description = "用户昵称")
    private String name;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "归属地")
    private String region;

    @Schema(description = "佩戴的徽章 id")
    private Integer itemId;
}
