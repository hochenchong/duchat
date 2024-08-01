package hochenchong.duchat.common.user.domain.vo.req.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * 批量查询用户详情
 *
 * @author hochenchong
 * @date 2024/08/01
 */
@Data
@Schema(description = "批量查询用户请求")
public class SummeryInfoReq {

    @Schema(description = "要查询的用户id")
    @Size(min = 1, max = 50)
    private List<Long> uidList;
}
