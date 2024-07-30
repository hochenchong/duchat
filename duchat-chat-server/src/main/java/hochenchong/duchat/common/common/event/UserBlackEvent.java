package hochenchong.duchat.common.common.event;

import hochenchong.duchat.common.user.domain.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 用户拉黑事件
 *
 * @author hochenchong
 * @date 2024/07/26
 */
@Getter
public class UserBlackEvent extends ApplicationEvent {
    private final User user;

    public UserBlackEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
}
