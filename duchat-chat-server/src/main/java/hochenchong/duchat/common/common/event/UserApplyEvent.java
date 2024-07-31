package hochenchong.duchat.common.common.event;

import hochenchong.duchat.common.user.domain.entity.UserApply;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 用户申请好友事件
 *
 * @author hochenchong
 * @date 2024/07/31
 */
@Getter
public class UserApplyEvent extends ApplicationEvent {
    private final UserApply userApply;

    public UserApplyEvent(Object source, UserApply userApply) {
        super(source);
        this.userApply = userApply;
    }
}
