package hochenchong.duchat.common.common.event.listener;

import hochenchong.duchat.common.common.event.UserOnlineEvent;
import hochenchong.duchat.common.user.dao.UserDao;
import hochenchong.duchat.common.user.domain.entity.User;
import hochenchong.duchat.common.user.domain.enums.UserStatusEnum;
import hochenchong.duchat.common.user.service.IpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * 用户上线事件监听器
 *
 * @author hochenchong
 * @date 2024/07/26
 */
@Component
public class UserOnlineListener {
    @Autowired
    private UserDao userDao;
    @Autowired
    private IpService ipService;

    @Async
    @TransactionalEventListener(classes = UserOnlineEvent.class, phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void saveDB(UserOnlineEvent event) {
        User user = event.getUser();
        // 保存用户上线的信息
        User update = new User();
        update.setId(user.getId());
        update.setLastOptTime(user.getLastOptTime());
        update.setIpInfo(user.getIpInfo());
        update.setActiveStatus(UserStatusEnum.ONLINE.getStatus());
        userDao.updateById(update);
        // 更新用户 ip 详情
        ipService.refreshIpDetailAsync(user.getId());
    }
}
