package hochenchong.duchat.common.user.service.adapter;

import hochenchong.duchat.common.user.domain.entity.User;
import hochenchong.duchat.common.websocket.domain.enums.WSRespTypeEnum;
import hochenchong.duchat.common.websocket.domain.vo.resp.WSBaseResp;
import hochenchong.duchat.common.websocket.domain.vo.resp.WSLoginSuccess;
import hochenchong.duchat.common.websocket.domain.vo.resp.WSLoginUrl;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;

/**
 * WebSocket 消息适配器
 *
 * @author hochenchong
 * @date 2024/07/23
 */
public class WebSocketAdapter {
    public static <T> WSBaseResp<T> buildResp(WSRespTypeEnum typeEnum, T data) {
        WSBaseResp<T> resp = new WSBaseResp<>();
        resp.setType(typeEnum.getType());
        resp.setData(data);
        return resp;
    }

    public static WSBaseResp<?> buildResp(WxMpQrCodeTicket wxMpQrCodeTicket) {
        WSBaseResp<WSLoginUrl> resp = new WSBaseResp<>();
        resp.setType(WSRespTypeEnum.LOGIN_URL.getType());
        resp.setData(new WSLoginUrl(wxMpQrCodeTicket.getUrl()));
        return resp;
    }

    public static WSBaseResp<?> buildLoginSuccessResp(User user, String token) {
        WSBaseResp<WSLoginSuccess> wsBaseResp = new WSBaseResp<>();
        wsBaseResp.setType(WSRespTypeEnum.LOGIN_SUCCESS.getType());
        WSLoginSuccess wsLoginSuccess = WSLoginSuccess.builder()
                .avatar(user.getAvatar())
                .name(user.getName())
                .token(token)
                .uid(user.getId())
                .build();
        wsBaseResp.setData(wsLoginSuccess);
        return wsBaseResp;
    }
}
