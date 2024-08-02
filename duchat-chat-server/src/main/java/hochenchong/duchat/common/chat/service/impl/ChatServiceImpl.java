package hochenchong.duchat.common.chat.service.impl;

import hochenchong.duchat.common.chat.dao.MessageDao;
import hochenchong.duchat.common.chat.dao.RoomFriendDao;
import hochenchong.duchat.common.chat.domain.entity.Message;
import hochenchong.duchat.common.chat.domain.entity.Room;
import hochenchong.duchat.common.chat.domain.entity.RoomFriend;
import hochenchong.duchat.common.chat.domain.vo.req.ChatMsgReq;
import hochenchong.duchat.common.chat.domain.vo.resp.ChatMsgResp;
import hochenchong.duchat.common.chat.service.ChatService;
import hochenchong.duchat.common.chat.service.adapter.MessageAdapter;
import hochenchong.duchat.common.chat.service.cache.RoomCache;
import hochenchong.duchat.common.chat.service.strategy.msg.AbstractMsgHandler;
import hochenchong.duchat.common.chat.service.strategy.msg.MsgHandlerFactory;
import hochenchong.duchat.common.common.domain.enums.TF;
import hochenchong.duchat.common.common.event.MsgSendEvent;
import hochenchong.duchat.common.common.exception.CustomErrorEnum;
import hochenchong.duchat.common.common.utils.AssertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * @author hochenchong
 * @date 2024/08/02
 */
@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private RoomCache roomCache;
    @Autowired
    private RoomFriendDao roomFriendDao;
    @Autowired
    private MessageDao messageDao;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public Long sendMsg(Long uid, ChatMsgReq req) {
        check(uid, req);
        AbstractMsgHandler<?> msgHandler = MsgHandlerFactory.getStrategyNotNull(req.getMsgType());
        Long msgId = msgHandler.checkAndSaveMsg(uid, req);
        applicationEventPublisher.publishEvent(new MsgSendEvent(this, msgId));
        return msgId;
    }

    @Override
    public ChatMsgResp getMsgResp(Long msgId) {
        Message msg = messageDao.getById(msgId);
        return MessageAdapter.buildMsgResp(msg);
    }

    private void check(Long uid, ChatMsgReq req) {
        Room room = roomCache.get(req.getRoomId());
        AssertUtils.isNotEmpty(room, CustomErrorEnum.PARAM_INVALID);
        // 单聊校验
        if (room.isRoomFriend()) {
            RoomFriend roomFriend = roomFriendDao.getByRoomId(req.getRoomId());
            AssertUtils.equal(TF.NO.getStatus(), roomFriend.getDeleteStatus(), CustomErrorEnum.NOT_FRIEND);
            AssertUtils.isTrue(uid.equals(roomFriend.getUid1()) || uid.equals(roomFriend.getUid2()), CustomErrorEnum.NOT_FRIEND);
            return;
        }
    }
}
