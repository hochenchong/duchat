package hochenchong.duchat.common.user.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import hochenchong.duchat.common.common.config.ThreadPoolConfig;
import hochenchong.duchat.common.common.domain.enums.TF;
import hochenchong.duchat.common.common.event.UserOnlineEvent;
import hochenchong.duchat.common.user.dao.UserDao;
import hochenchong.duchat.common.user.domain.entity.User;
import hochenchong.duchat.common.user.domain.enums.RoleEnum;
import hochenchong.duchat.common.user.service.LoginService;
import hochenchong.duchat.common.user.service.RoleService;
import hochenchong.duchat.common.user.service.WebSocketService;
import hochenchong.duchat.common.user.service.adapter.WebSocketAdapter;
import hochenchong.duchat.common.websocket.NettyUtil;
import hochenchong.duchat.common.websocket.domain.dto.WSChannelExtraDTO;
import hochenchong.duchat.common.websocket.domain.enums.WSRespTypeEnum;
import hochenchong.duchat.common.websocket.domain.vo.req.WSBaseReq;
import hochenchong.duchat.common.websocket.domain.vo.resp.WSBaseResp;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.SneakyThrows;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket 服务统一管理，包括推拉
 *
 * @author hochenchong
 * @date 2024/07/23
 */
@Service
public class WebSocketServiceImpl implements WebSocketService {

    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private RoleService roleService;
    @Autowired
    @Qualifier(ThreadPoolConfig.WS_EXECUTOR)
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 管理所有用户的连接（登录/游客）
     */
    private static final ConcurrentHashMap<Channel, WSChannelExtraDTO> ONLINE_WS_MAP = new ConcurrentHashMap<>();

    public static final int MAXIMUM_SIZE = 1000;

    public static final Duration DURATION = Duration.ofHours(1);

    private static final Cache<Integer, Channel> WAIT_LOGIN_MAP = Caffeine.newBuilder()
            .maximumSize(MAXIMUM_SIZE)
            .expireAfterWrite(DURATION)
            .build();

    @SneakyThrows
    @Override
    public void handleLoginReq(Channel channel) {
        // 生成随机码
        Integer code = generateLoginCode(channel);
        // 微信登录的话，申请带参二维码
        WxMpQrCodeTicket wxMpQrCodeTicket = wxMpService.getQrcodeService().qrCodeCreateLastTicket(code);
        WSBaseResp<?> resp = WebSocketAdapter.buildResp(wxMpQrCodeTicket);
        sendMsg(channel, resp);
    }

    /**
     * 用户通过账号密码登录
     *
     * @param channel 用户连接的 channel
     * @param wsBaseReq 用户登录携带的信息
     */
    @Override
    public void userLoginReq(Channel channel, WSBaseReq wsBaseReq) {
        String data = wsBaseReq.getData();
        if (StringUtils.isBlank(data)) {
            sendMsg(channel, WebSocketAdapter.buildResp(WSRespTypeEnum.LOGIN_ERROR, "数据有误"));
            return;
        }
        User user = JSONUtil.toBean(data, User.class);
        user = loginService.userLogin(user.getName(), user.getPassword());
        if (user == null) {
            sendMsg(channel, WebSocketAdapter.buildResp(WSRespTypeEnum.LOGIN_ERROR, "用户名或密码不正确！"));
            return;
        }
        if (user.getStatus() == TF.YES.getStatus()) {
            sendMsg(channel, WebSocketAdapter.buildResp(WSRespTypeEnum.LOGIN_ERROR, "用户已被禁止使用！"));
            return;
        }
        // 成功登录，则获取 token
        String token = loginService.login(user.getId());
        loginSuccess(channel, user, token);
    }

    private void loginSuccess(Channel channel, User user, String token) {
        // 更新上线列表
        online(channel, user.getId());
        // 返回用户登录成功信息
        sendMsg(channel, WebSocketAdapter.buildLoginSuccessResp(user, token, roleService.hasPower(user.getId(), RoleEnum.CHAT_MANAGER)));
        // 用户上线成功事件
        user.setLastOptTime(LocalDateTime.now());
        user.refreshIp(NettyUtil.getAttr(channel, NettyUtil.IP));
        applicationEventPublisher.publishEvent(new UserOnlineEvent(this, user));
    }

    /**
     * 用户上线
     */
    private void online(Channel channel, Long uid) {
        getOrInitChannelExt(channel).setUid(uid);
        NettyUtil.setAttr(channel, NettyUtil.UID, uid);
    }

    /**
     * 从在线列表里获取信息
     *     不存在，则将该 channel 放入
     *
     * @param channel 用户连接
     * @return 连接信息
     */
    private WSChannelExtraDTO getOrInitChannelExt(Channel channel) {
        WSChannelExtraDTO wsChannelExtraDTO = ONLINE_WS_MAP.getOrDefault(channel, new WSChannelExtraDTO());
        WSChannelExtraDTO old = ONLINE_WS_MAP.putIfAbsent(channel, wsChannelExtraDTO);
        return ObjectUtil.isNull(old) ? wsChannelExtraDTO : old;
    }

    private Integer generateLoginCode(Channel channel) {
        int code;
        do {
            code = RandomUtil.randomInt(Integer.MAX_VALUE);
        } while (Objects.nonNull(WAIT_LOGIN_MAP.asMap().putIfAbsent(code, channel)));
        return code;
    }

    private void sendMsg(Channel channel, WSBaseResp<?> resp) {
        channel.writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(resp)));
    }

    @Override
    public void connect(Channel channel) {
        ONLINE_WS_MAP.put(channel, new WSChannelExtraDTO());
    }

    @Override
    public void remove(Channel channel) {

    }

    @Override
    public void authorize(Channel channel, String token) {
        // 校验 token
        boolean verify = loginService.verify(token);
        if (verify) {
            // 通过 token 获取 uid，如果获取到用户，则登录
            User user = userDao.getById(loginService.getValidUid(token));
            if (user != null) {
                loginSuccess(channel, user, token);
                return;
            }
        }
        sendMsg(channel, WebSocketAdapter.buildResp(WSRespTypeEnum.INVALIDATE_TOKEN));
    }

    @Override
    public void sendMsgToAll(WSBaseResp<?> msg) {
        ONLINE_WS_MAP.forEach((channel, ext) -> {
            threadPoolTaskExecutor.execute(() -> {
                sendMsg(channel, msg);
            });
        });
    }
}
