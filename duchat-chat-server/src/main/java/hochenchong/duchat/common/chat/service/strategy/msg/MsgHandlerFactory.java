package hochenchong.duchat.common.chat.service.strategy.msg;

import hochenchong.duchat.common.common.exception.CustomErrorEnum;
import hochenchong.duchat.common.common.utils.AssertUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息工厂
 * @author hochenchong
 * @date 2024/08/02
 */
public class MsgHandlerFactory {
    private static final Map<Integer, AbstractMsgHandler> MSG_HANDLER_MAP = new HashMap<>();


    public static <T> void register(Integer msgType, AbstractMsgHandler<T> handler) {
        MSG_HANDLER_MAP.put(msgType, handler);
    }

    public static AbstractMsgHandler getStrategyNotNull(Integer type) {
        AbstractMsgHandler strategy = MSG_HANDLER_MAP.get(type);
        AssertUtils.isNotEmpty(strategy, CustomErrorEnum.PARAM_INVALID);
        return strategy;
    }
}