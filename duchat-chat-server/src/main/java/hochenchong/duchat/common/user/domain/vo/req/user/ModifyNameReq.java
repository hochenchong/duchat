package hochenchong.duchat.common.user.domain.vo.req.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 修改用户名请求
 *
 * @author hochenchong
 * @date 2024/07/25
 */
@Data
public class ModifyNameReq {
    @Schema(description = "用户名")
    @NotNull
    @Length(max = 6, message = "用户名过长！")
    private String name;
}