package hochenchong.duchat.common.websocket;

import cn.hutool.json.JSONUtil;
import hochenchong.duchat.common.websocket.domain.enums.WSReqTypeEnum;
import hochenchong.duchat.common.websocket.domain.vo.req.WSBaseReq;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class NettyWebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String text = msg.text();
        WSBaseReq wsBaseReq = JSONUtil.toBean(text, WSBaseReq.class);
        switch (WSReqTypeEnum.of(wsBaseReq.getType())) {
            case LOGIN -> {
                System.out.println("请求登录的二维码");
                ctx.channel().writeAndFlush(new TextWebSocketFrame("测试数据"));
            }
            case AUTHORIZE -> {

            }
            case HEARTBEAT -> {

            }
            default -> {
            }
        }
    }
}
