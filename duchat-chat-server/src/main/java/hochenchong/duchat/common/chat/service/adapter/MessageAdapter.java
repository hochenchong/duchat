package hochenchong.duchat.common.chat.service.adapter;

import hochenchong.duchat.common.chat.domain.entity.Message;
import hochenchong.duchat.common.chat.domain.enums.MsgStatusEnum;
import hochenchong.duchat.common.chat.domain.vo.req.ChatMsgReq;
import hochenchong.duchat.common.chat.domain.vo.resp.ChatMsgResp;
import hochenchong.duchat.common.chat.service.strategy.msg.AbstractMsgHandler;
import hochenchong.duchat.common.chat.service.strategy.msg.MsgHandlerFactory;
import org.springframework.beans.BeanUtils;

/**
 * @author hochenchong
 * @date 2024/08/02
 */
public class MessageAdapter {
    public static Message buildMsgSave(ChatMsgReq msgReq, Long uid) {
        return Message.builder()
                .fromUid(uid)
                .roomId(msgReq.getRoomId())
                .type(msgReq.getMsgType())
                .status(MsgStatusEnum.NORMAL.getStatus())
                .build();
    }

    public static ChatMsgResp buildMsgResp(Message msg) {
        ChatMsgResp resp = new ChatMsgResp();
        BeanUtils.copyProperties(msg, resp);
        AbstractMsgHandler<?> msgHandler = MsgHandlerFactory.getStrategyNotNull(msg.getType());
        resp.setMsgContent(msgHandler.showMsg(msg));
        return resp;
    }
}
