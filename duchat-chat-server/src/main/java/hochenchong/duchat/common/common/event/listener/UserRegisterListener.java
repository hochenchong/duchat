package hochenchong.duchat.common.common.event.listener;

import hochenchong.duchat.common.common.event.UserRegisterEvent;
import hochenchong.duchat.common.user.domain.entity.User;
import hochenchong.duchat.common.user.domain.enums.IdempotentEnum;
import hochenchong.duchat.common.user.domain.enums.ItemEnum;
import hochenchong.duchat.common.user.service.UserBackpackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * 用户注册事件监听器
 *
 * @author hochenchong
 * @date 2024/07/26
 */
@Component
public class UserRegisterListener {
    @Autowired
    private UserBackpackService userBackpackService;

    // 可以配置同时监听多个事件，或者不配置，但方法的参数类型必须与所监听的事件类型一致或兼容
    @EventListener(classes = {
            UserRegisterEvent.class
    })
    public void sendCard(UserRegisterEvent event) {
        User user = event.getUser();
        // 发送更名卡
        userBackpackService.acquireItem(user.getId(), ItemEnum.MODIFY_NAME_CARD.getId(), IdempotentEnum.UID, user.getId().toString());
    }

    // 可以使用异步的方式，如果发放事件在事务中，还可以选择在事务的什么阶段执行，默认在事务提交后执行
    @Async
    // @EventListener(classes = UserRegisterListener.class)
    @TransactionalEventListener(classes = UserRegisterEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void sendBadge(UserRegisterEvent event) {
        User user = event.getUser();
        // 前 100 名注册有徽章
        // 这里用户id 是自动递增的，直接通过 id 来判断，就不查库了，避免后期用户数过大时，性能较差，或者后期直接注释掉此代码
        if (user.getId() <= 100L) {
            userBackpackService.acquireItem(user.getId(), ItemEnum.REG_TOP100_BADGE.getId(), IdempotentEnum.UID, user.getId().toString());
        }
    }
}
