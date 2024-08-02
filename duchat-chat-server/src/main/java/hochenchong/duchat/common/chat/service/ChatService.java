package hochenchong.duchat.common.chat.service;

import hochenchong.duchat.common.chat.domain.vo.req.ChatMsgReq;
import hochenchong.duchat.common.chat.domain.vo.resp.ChatMsgResp;

/**
 * @author hochenchong
 * @date 2024/08/02
 */
public interface ChatService {
    /**
     * 用户发送消息，返回消息 id
     * @param uid 用户 id
     * @param req 消息
     * @return 消息 id
     */
    Long sendMsg(Long uid, ChatMsgReq req);

    /**
     * 根据消息 id 获取消息回包
     *
     * @param msgId 消息 id
     * @return 消息回包
     */
    ChatMsgResp getMsgResp(Long msgId);
}
