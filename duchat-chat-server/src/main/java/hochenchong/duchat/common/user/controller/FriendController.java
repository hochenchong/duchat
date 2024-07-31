package hochenchong.duchat.common.user.controller;

import hochenchong.duchat.common.common.domain.vo.request.CursorPageBaseReq;
import hochenchong.duchat.common.common.domain.vo.request.PageBaseReq;
import hochenchong.duchat.common.common.domain.vo.response.ApiResult;
import hochenchong.duchat.common.common.domain.vo.response.CursorPageBaseResp;
import hochenchong.duchat.common.common.domain.vo.response.PageBaseResp;
import hochenchong.duchat.common.common.utils.RequestHolder;
import hochenchong.duchat.common.user.domain.vo.req.friend.FriendApplyReq;
import hochenchong.duchat.common.user.domain.vo.req.friend.FriendApproveReq;
import hochenchong.duchat.common.user.domain.vo.req.friend.FriendCheckReq;
import hochenchong.duchat.common.user.domain.vo.req.friend.FriendDeleteReq;
import hochenchong.duchat.common.user.domain.vo.resp.friend.FriendApplyResp;
import hochenchong.duchat.common.user.domain.vo.resp.friend.FriendCheckResp;
import hochenchong.duchat.common.user.domain.vo.resp.friend.FriendResp;
import hochenchong.duchat.common.user.domain.vo.resp.friend.FriendUnreadResp;
import hochenchong.duchat.common.user.service.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 好友模块
 *
 * @author hochenchong
 * @date 2024/07/31
 */
@RestController
@RequestMapping("/capi/user/friend")
@Tag(name = "好友模块")
public class FriendController {
    @Autowired
    private FriendService friendService;

    @PostMapping("/apply")
    @Operation(summary = "申请好友")
    public ApiResult<Void> apply(@Valid @RequestBody FriendApplyReq request) {
        Long uid = RequestHolder.get().getUid();
        friendService.apply(uid, request);
        return ApiResult.success();
    }

    @PutMapping("/apply")
    @Operation(summary = "同意好友")
    public ApiResult<Void> approve(@Valid @RequestBody FriendApproveReq request) {
        friendService.approve(RequestHolder.get().getUid(), request.getApplyId());
        return ApiResult.success();
    }

    @DeleteMapping()
    @Operation(summary = "删除好友")
    public ApiResult<Void> delete(@Valid @RequestBody FriendDeleteReq request) {
        Long uid = RequestHolder.get().getUid();
        friendService.deleteFriend(uid, request.getUid());
        return ApiResult.success();
    }

    @GetMapping("/check")
    @Operation(summary = "批量判断好友")
    public ApiResult<FriendCheckResp> check(@Valid @RequestBody FriendCheckReq req) {
        Long uid = RequestHolder.get().getUid();
        return ApiResult.success(friendService.check(uid, req.getIds()));
    }

    @GetMapping("/apply/page")
    @Operation(summary = "好友申请列表")
    public ApiResult<PageBaseResp<FriendApplyResp>> page(@Valid PageBaseReq request) {
        Long uid = RequestHolder.get().getUid();
        return ApiResult.success(friendService.pageApplyFriend(uid, request));
    }

    @GetMapping("/apply/unread")
    @Operation(summary = "申请未读数")
    public ApiResult<FriendUnreadResp> unread() {
        Long uid = RequestHolder.get().getUid();
        return ApiResult.success(friendService.unread(uid));
    }

    @GetMapping("/page")
    @Operation(summary = "联系人列表")
    public ApiResult<CursorPageBaseResp<FriendResp>> friendList(@Valid CursorPageBaseReq request) {
        Long uid = RequestHolder.get().getUid();
        return ApiResult.success(friendService.friendList(uid, request));
    }
}
