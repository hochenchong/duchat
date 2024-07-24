package hochenchong.duchat.common.user.controller;

import hochenchong.duchat.common.common.domain.vo.response.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author hochenchong
 * @since 2024-03-19
 */
@Controller
@RequestMapping("/capi/user")
@Tag(name = "用户模块")
public class UserController {

    @GetMapping("/userInfo")
    @Operation(summary = "用户详情")
    public ApiResult getUserInfo() {
        return ApiResult.success();
    }
}
