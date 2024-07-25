package hochenchong.duchat.common.user.service.impl;

import hochenchong.duchat.common.common.annotation.RedissonLock;
import hochenchong.duchat.common.common.domain.enums.TF;
import hochenchong.duchat.common.user.dao.UserBackpackDao;
import hochenchong.duchat.common.user.domain.entity.UserBackpack;
import hochenchong.duchat.common.user.domain.enums.IdempotentEnum;
import hochenchong.duchat.common.user.service.UserBackpackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author hochenchong
 * @date 2024/07/25
 */
@Service
// @EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class UserBackpackServiceImpl implements UserBackpackService {
    @Autowired
    private UserBackpackDao userBackpackDao;

    @Autowired
    @Lazy
    private UserBackpackServiceImpl userBackpackService;

    @Override
    public void acquireItem(Long uid, int itemId, IdempotentEnum idempotentEnum, String businessId) {
        String idempotent = getIdempotent(itemId, idempotentEnum, businessId);
        UserBackpack userBackpack = userBackpackDao.getByIdempotent(idempotent);
        if (Objects.nonNull(userBackpack)) {
            return;
        }
        // 方法一：通过注解 @EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true) 开启代理，然后从上下文中获取
        // ((UserBackpackServiceImpl) AopContext.currentProxy()).doAcquireItem(uid, itemId, idempotent);
        // 方法二：懒加载注入自己
        userBackpackService.doAcquireItem(uid, itemId, idempotent);
    }

    @RedissonLock(key = "#idempotent", waitTime = 5000)
    public void doAcquireItem(Long uid, int itemId, String idempotent) {
        // 发放道具
        UserBackpack userBackpack = new UserBackpack();
        userBackpack.setUid(uid);
        userBackpack.setItemId(itemId);
        userBackpack.setStatus(TF.NO.getStatus());
        userBackpack.setIdempotent(idempotent);
        userBackpackDao.save(userBackpack);
    }

    private String getIdempotent(int itemId, IdempotentEnum idempotentEnum, String businessId) {
        return String.format("%d_%d_%s", itemId, idempotentEnum.getType(), businessId);
    }
}
