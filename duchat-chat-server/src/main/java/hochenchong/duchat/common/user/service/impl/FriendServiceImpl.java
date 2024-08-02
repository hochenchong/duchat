package hochenchong.duchat.common.user.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hochenchong.duchat.common.chat.domain.entity.RoomFriend;
import hochenchong.duchat.common.chat.service.RoomService;
import hochenchong.duchat.common.common.annotation.RedissonLock;
import hochenchong.duchat.common.common.domain.vo.request.CursorPageBaseReq;
import hochenchong.duchat.common.common.domain.vo.request.PageBaseReq;
import hochenchong.duchat.common.common.domain.vo.response.CursorPageBaseResp;
import hochenchong.duchat.common.common.domain.vo.response.PageBaseResp;
import hochenchong.duchat.common.common.event.UserApplyEvent;
import hochenchong.duchat.common.common.exception.CustomErrorEnum;
import hochenchong.duchat.common.common.utils.AssertUtils;
import hochenchong.duchat.common.user.dao.UserApplyDao;
import hochenchong.duchat.common.user.dao.UserFriendDao;
import hochenchong.duchat.common.user.domain.entity.UserApply;
import hochenchong.duchat.common.user.domain.entity.UserFriend;
import hochenchong.duchat.common.user.domain.enums.ApplyStatusEnum;
import hochenchong.duchat.common.user.domain.enums.ApplyTypeEnum;
import hochenchong.duchat.common.user.domain.vo.req.friend.FriendApplyReq;
import hochenchong.duchat.common.user.domain.vo.resp.friend.FriendApplyResp;
import hochenchong.duchat.common.user.domain.vo.resp.friend.FriendCheckResp;
import hochenchong.duchat.common.user.domain.vo.resp.friend.FriendResp;
import hochenchong.duchat.common.user.domain.vo.resp.friend.FriendUnreadResp;
import hochenchong.duchat.common.user.service.FriendService;
import hochenchong.duchat.common.user.service.adapter.FriendAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 好友服务
 *
 * @author hochenchong
 * @date 2024/07/31
 */
@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    private UserFriendDao userFriendDao;
    @Autowired
    private UserApplyDao userApplyDao;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    @Lazy
    private FriendService friendService;

    @Autowired
    private RoomService roomService;

    @Override
    public void apply(Long uid, FriendApplyReq req) {
        // 不能加自己
        AssertUtils.isFalse(Objects.equals(uid, req.getUid()), CustomErrorEnum.NOT_ADD_SELF);
        // 判断是否已经是好友
        UserFriend userFriend = userFriendDao.getByFriend(uid, req.getUid());
        AssertUtils.isEmpty(userFriend, CustomErrorEnum.ALREADY_FRIENDS);
        // 判断已经自己有待审批的申请记录，已经存在则直接返回
        UserApply userApply = userApplyDao.getFriendApply(uid, req.getUid());
        if (Objects.nonNull(userApply)) {
            return;
        }
        // 是否有对方的申请记录，有则同意好友
        userApply = userApplyDao.getFriendApply(req.getUid(), uid);
        if (Objects.nonNull(userApply)) {
            friendService.approve(uid, userApply.getId());
            return;
        }
        // 申请记录
        userApply = FriendAdapter.buildFriendApply(uid, req);
        userApplyDao.save(userApply);
        // 发送申请好友事件
        applicationEventPublisher.publishEvent(new UserApplyEvent(this, userApply));
    }

    @Transactional(rollbackFor = Exception.class)
    @RedissonLock(key = "#uid")
    @Override
    public void approve(Long uid, Long applyId) {
        UserApply userApply = userApplyDao.getById(applyId);
        AssertUtils.isNotEmpty(userApply, CustomErrorEnum.APPLY_RECORD_NOT_EXISTS);
        AssertUtils.isTrue(Objects.equals(uid, userApply.getTargetId()), CustomErrorEnum.APPLY_RECORD_NOT_EXISTS);
        AssertUtils.isTrue(Objects.equals(ApplyTypeEnum.ADD_FRIEND.getCode(), userApply.getType()), CustomErrorEnum.APPLY_RECORD_NOT_EXISTS);
        AssertUtils.isTrue(Objects.equals(ApplyStatusEnum.PENDING.getCode(), userApply.getStatus()), CustomErrorEnum.APPLY_RECORD_APPROVAL);
        // 同意申请
        userApplyDao.agree(applyId);
        // 创建好友记录，两条记录
        createFriend(userApply.getUid(), userApply.getTargetId());
        // 添加好友后，创建房间
        RoomFriend roomFriend = roomService.createFriendRoom(userApply.getUid(), userApply.getTargetId());
    }

    /**
     * 删除好友
     *
     * @param uid 用户id
     * @param friendUid 好友用户 id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFriend(Long uid, Long friendUid) {
        List<UserFriend> friendIds = userFriendDao.getFriendIds(uid, friendUid);
        if (CollectionUtils.isEmpty(friendIds)) {
            // 没有好友关系，直接返回即可
            return;
        }
        userFriendDao.removeByIds(friendIds.stream().map(UserFriend::getId).toList());
        // 禁用房间
        roomService.delFriendRoom(uid, friendUid);
    }

    @Override
    public FriendCheckResp check(Long uid, List<Long> uidList) {
        FriendCheckResp resp = new FriendCheckResp();
        if (CollectionUtils.isEmpty(uidList)) {
            return resp;
        }
        // 根据这批 uid 获取是否存在好友表数据，存在则为好友
        List<UserFriend> friends = userFriendDao.getByFriends(uid, uidList);
        Set<Long> friendUidSet = friends.stream().map(UserFriend::getFriendUid).collect(Collectors.toSet());
        List<FriendCheckResp.FriendCheck> friendCheckList = uidList.stream().map(id -> {
            FriendCheckResp.FriendCheck friendCheck = new FriendCheckResp.FriendCheck();
            friendCheck.setUid(id);
            friendCheck.setIsFriend(friendUidSet.contains(id));
            return friendCheck;
        }).toList();
        resp.setChecks(friendCheckList);
        return resp;
    }

    @Override
    public PageBaseResp<FriendApplyResp> pageApplyFriend(Long uid, PageBaseReq request) {
        Page<UserApply> page = userApplyDao.friendApplyPage(uid, request.page());
        if (CollectionUtils.isEmpty(page.getRecords())) {
            return PageBaseResp.empty();
        }
        // 将获取的申请为已读
        userApplyDao.readApply(uid, page.getRecords().stream().map(UserApply::getId).toList());
        return PageBaseResp.init(page, FriendAdapter.buildFriendApplyList(page.getRecords()));
    }

    @Override
    public FriendUnreadResp unread(Long uid) {
        return new FriendUnreadResp(userApplyDao.getUnReadCount(uid));
    }

    @Override
    public CursorPageBaseResp<FriendResp> friendList(Long uid, CursorPageBaseReq request) {
        CursorPageBaseResp<UserFriend> friendPage = userFriendDao.getFriendPage(uid, request);
        if (CollectionUtils.isEmpty(friendPage.getList())) {
            return CursorPageBaseResp.empty();
        }
        List<FriendResp> list = friendPage.getList().stream().map(f -> FriendResp.builder().uid(f.getFriendUid()).build()).toList();
        return CursorPageBaseResp.init(friendPage, list);
    }

    private void createFriend(Long uid, Long targetUid) {
        UserFriend userFriend1 = new UserFriend();
        userFriend1.setUid(uid);
        userFriend1.setFriendUid(targetUid);
        UserFriend userFriend2 = new UserFriend();
        userFriend2.setUid(targetUid);
        userFriend2.setFriendUid(uid);
        userFriendDao.saveBatch(Arrays.asList(userFriend1, userFriend2));
    }
}
