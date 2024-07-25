package hochenchong.duchat.common.user.controller;

import hochenchong.duchat.common.common.domain.vo.response.ApiResult;
import hochenchong.duchat.common.common.utils.RequestHolder;
import hochenchong.duchat.common.user.domain.vo.req.ModifyNameReq;
import hochenchong.duchat.common.user.domain.vo.resp.UserInfoResp;
import hochenchong.duchat.common.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author hochenchong
 * @since 2024-03-19
 */
@RestController
@RequestMapping("/capi/user")
@Tag(name = "用户模块")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    @Operation(summary = "用户详情")
    public ApiResult<UserInfoResp> getUserInfo() {
        return ApiResult.success(userService.getUserInfo(RequestHolder.get().getUid()));
    }

    @PutMapping("/nickname")
    @Operation(summary = "修改用户昵称")
    public ApiResult<Void> modifyNickname(@Valid @RequestBody ModifyNameReq req) {
        userService.modifyNickname(RequestHolder.get().getUid(), req.getName());
        return ApiResult.success();
    }
}
