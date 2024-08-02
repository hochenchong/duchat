package hochenchong.duchat.common.common.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 消息发送事件
 *
 * @author hochenchong
 * @date 2024/08/02
 */
@Getter
public class MsgSendEvent extends ApplicationEvent {
    private final Long msgId;

    public MsgSendEvent(Object source, Long msgId) {
        super(source);
        this.msgId = msgId;
    }
}
