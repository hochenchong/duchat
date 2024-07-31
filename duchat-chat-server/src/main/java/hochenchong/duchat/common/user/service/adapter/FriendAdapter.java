package hochenchong.duchat.common.user.service.adapter;

import hochenchong.duchat.common.user.domain.entity.UserApply;
import hochenchong.duchat.common.user.domain.enums.ApplyReadStatusEnum;
import hochenchong.duchat.common.user.domain.enums.ApplyStatusEnum;
import hochenchong.duchat.common.user.domain.enums.ApplyTypeEnum;
import hochenchong.duchat.common.user.domain.vo.req.friend.FriendApplyReq;
import hochenchong.duchat.common.user.domain.vo.resp.friend.FriendApplyResp;

import java.util.List;

/**
 * @author hochenchong
 * @date 2024/07/31
 */
public class FriendAdapter {
    public static UserApply buildFriendApply(Long uid, FriendApplyReq req) {
        return UserApply.builder()
                .uid(uid)
                .type(ApplyTypeEnum.ADD_FRIEND.getCode())
                .targetId(req.getUid())
                .msg(req.getMsg())
                .status(ApplyStatusEnum.PENDING.getCode())
                .readStatus(ApplyReadStatusEnum.UNREAD.getCode()).build();
    }

    public static List<FriendApplyResp> buildFriendApplyList(List<UserApply> records) {
        return records.stream().map(userApply ->
                FriendApplyResp.builder()
                        .uid(userApply.getUid())
                        .type(userApply.getType())
                        .applyId(userApply.getId())
                        .msg(userApply.getMsg())
                        .status(userApply.getStatus()).build()).toList();
    }
}
