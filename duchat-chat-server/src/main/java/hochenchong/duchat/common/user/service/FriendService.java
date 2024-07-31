package hochenchong.duchat.common.user.service;

import hochenchong.duchat.common.common.domain.vo.request.CursorPageBaseReq;
import hochenchong.duchat.common.common.domain.vo.request.PageBaseReq;
import hochenchong.duchat.common.common.domain.vo.response.CursorPageBaseResp;
import hochenchong.duchat.common.common.domain.vo.response.PageBaseResp;
import hochenchong.duchat.common.user.domain.vo.req.friend.FriendApplyReq;
import hochenchong.duchat.common.user.domain.vo.resp.friend.FriendApplyResp;
import hochenchong.duchat.common.user.domain.vo.resp.friend.FriendCheckResp;
import hochenchong.duchat.common.user.domain.vo.resp.friend.FriendResp;
import hochenchong.duchat.common.user.domain.vo.resp.friend.FriendUnreadResp;

import java.util.List;

/**
 * 好友服务
 *
 * @author hochenchong
 * @date 2024/07/31
 */
public interface FriendService {
    /**
     * 申请好友
     *
     * @param uid 当前用户 uid
     * @param request 申请的用户信息
     */
    void apply(Long uid, FriendApplyReq request);

    /**
     * 同意好友申请
     *
     * @param uid 用户 uid
     * @param applyId 申请记录id
     */
    void approve(Long uid, Long applyId);

    /**
     * 删除好友
     *
     * @param uid 用户id
     * @param friendUid 好友用户 id
     */
    void deleteFriend(Long uid, Long friendUid);

    FriendCheckResp check(Long uid, List<Long> ids);

    /**
     * 分页查询申请列表
     *
     * @param uid 用户 id
     * @param request 请求
     * @return 申请列表
     */
    PageBaseResp<FriendApplyResp> pageApplyFriend(Long uid, PageBaseReq request);

    /**
     * 用户未读好友申请数量
     *
     * @param uid 用户 id
     * @return 未读好友申请数量，最多显示 99
     */
    FriendUnreadResp unread(Long uid);

    /**
     * 通过游标的方式，获取好友列表
     *
     * @param uid 用户 id
     * @param request 请求
     * @return 好友列表
     */
    CursorPageBaseResp<FriendResp> friendList(Long uid, CursorPageBaseReq request);
}
