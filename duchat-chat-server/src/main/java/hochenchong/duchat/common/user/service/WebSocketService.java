package hochenchong.duchat.common.user.service;

import hochenchong.duchat.common.websocket.domain.vo.req.WSBaseReq;
import hochenchong.duchat.common.websocket.domain.vo.resp.WSBaseResp;
import io.netty.channel.Channel;

/**
 * @author hochenchong
 * @date 2024/07/23
 */
public interface WebSocketService {
    /**
     * 处理用户登录请求
     *
     * @param channel 用户连接的 channel
     */
    void handleLoginReq(Channel channel);

    /**
     * 用户登录
     *
     * @param channel 用户连接的 channel
     * @param wsBaseReq 用户登录携带的信息
     */
    void userLoginReq(Channel channel, WSBaseReq wsBaseReq);

    /**
     * 处理所有 websocket 连接事件
     *
     * @param channel 用户连接的 channel
     */
    void connect(Channel channel);

    /**
     * 处理 websocket 断开连接事件
     *
     * @param channel 用户连接的 channel
     */
    void remove(Channel channel);

    void authorize(Channel channel, String token);

    void sendMsgToAll(WSBaseResp<?> msg);
}
