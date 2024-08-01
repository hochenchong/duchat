package hochenchong.duchat.common.user.controller;

import hochenchong.duchat.common.common.domain.vo.response.ApiResult;
import hochenchong.duchat.common.common.exception.CustomErrorEnum;
import hochenchong.duchat.common.common.utils.AssertUtils;
import hochenchong.duchat.common.common.utils.RequestHolder;
import hochenchong.duchat.common.user.domain.dto.BadgeItemDTO;
import hochenchong.duchat.common.user.domain.dto.SummeryInfoDTO;
import hochenchong.duchat.common.user.domain.enums.RoleEnum;
import hochenchong.duchat.common.user.domain.vo.req.user.BlackReq;
import hochenchong.duchat.common.user.domain.vo.req.user.ModifyNameReq;
import hochenchong.duchat.common.user.domain.vo.req.user.WearingBadgeReq;
import hochenchong.duchat.common.user.domain.vo.resp.user.BadgeResp;
import hochenchong.duchat.common.user.domain.vo.req.user.SummeryInfoReq;
import hochenchong.duchat.common.user.domain.vo.resp.user.UserInfoResp;
import hochenchong.duchat.common.user.service.RoleService;
import hochenchong.duchat.common.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @Autowired
    private RoleService roleService;

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

    @GetMapping("/badges")
    @Operation(summary = "用户徽章列表")
    public ApiResult<List<BadgeResp>> badges() {
        return ApiResult.success(userService.badges(RequestHolder.get().getUid()));
    }

    @PutMapping("/badge")
    @Operation(summary = "佩戴徽章")
    public ApiResult<Void> wearingBadge(@Valid @RequestBody WearingBadgeReq req) {
        userService.wearingBadge(RequestHolder.get().getUid(), req.getBadgeId());
        return ApiResult.success();
    }

    @PutMapping("/black")
    @Operation(summary = "拉黑用户")
    public ApiResult<Void> black(@Valid @RequestBody BlackReq req) {
        // 先判断是否有权限
        boolean hasPower = roleService.hasPower(RequestHolder.get().getUid(), RoleEnum.CHAT_MANAGER);
        AssertUtils.isTrue(hasPower, CustomErrorEnum.NO_PERMISSION);
        // 拉黑用户
        userService.black(req);
        return ApiResult.success();
    }

    @GetMapping("/public/userInfo/batch")
    @Operation(summary = "批量查询用户信息")
    public ApiResult<List<SummeryInfoDTO>> getSummeryUserInfo(@Valid @RequestBody SummeryInfoReq req) {
        return ApiResult.success(userService.getSummeryUserInfo(req));
    }

    @GetMapping("/public/badges")
    @Operation(summary = "所有徽章信息")
    public ApiResult<List<BadgeItemDTO>> getBadgeItemInfos() {
        return ApiResult.success(userService.getBadgeItemInfos());
    }
}
