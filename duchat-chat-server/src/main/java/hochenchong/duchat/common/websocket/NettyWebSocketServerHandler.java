package hochenchong.duchat.common.websocket;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import hochenchong.duchat.common.user.service.WebSocketService;
import hochenchong.duchat.common.websocket.domain.enums.WSReqTypeEnum;
import hochenchong.duchat.common.websocket.domain.vo.req.WSBaseReq;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.apache.commons.lang3.StringUtils;

@ChannelHandler.Sharable
public class NettyWebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private WebSocketService webSocketService;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 建立连接时做的事情
        this.webSocketService = SpringUtil.getBean(WebSocketService.class);
        super.channelActive(ctx);
    }

    /**
     * 监听事件
     *
     * @param ctx 上下文
     * @param evt 事件
     * @throws Exception 异常
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 空闲时间到则下线处理
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                System.out.println("读空闲");
                // 用户下线
                userOffLine(ctx.channel());
            }
        } else if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            // 握手完成时
            this.webSocketService.connect(ctx.channel());
            // 判断该 channel 是否携带 token 信息
            String token = NettyUtil.getAttr(ctx.channel(), NettyUtil.TOKEN);
            if (StringUtils.isNotBlank(token)) {
                this.webSocketService.authorize(ctx.channel(), token);
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    /**
     * 读取客户端发送的请求消息
     *
     * @param ctx 上下文
     * @param msg 消息
     * @throws Exception 异常
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String text = msg.text();
        WSBaseReq wsBaseReq = JSONUtil.toBean(text, WSBaseReq.class);
        // java14 switch表达式增强
        switch (WSReqTypeEnum.of(wsBaseReq.getType())) {
            case LOGIN -> {
                // System.out.println("请求登录的二维码");
                // 这里使用用户密码登录
                // handlerLogin(ctx, wsBaseReq);
                this.webSocketService.userLoginReq(ctx.channel(), wsBaseReq);
            }
            case AUTHORIZE -> {

            }
            case HEARTBEAT -> {

            }
            default -> {
            }
        }
    }

    /**
     * 用户下线操作
     *
     * @param channel 用户channel
     */
    private void userOffLine(Channel channel) {
        this.webSocketService.remove(channel);
        channel.close();
    }
}
