package hochenchong.duchat.common.user.service.impl;

import hochenchong.duchat.common.user.domain.enums.RoleEnum;
import hochenchong.duchat.common.user.service.RoleService;
import hochenchong.duchat.common.user.service.cache.UserCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 用户角色服务
 *
 * @author hochenchong
 * @date 2024/07/30
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private UserCache userCache;

    @Override
    public boolean hasPower(Long uid, RoleEnum roleEnum) {
        Set<Integer> roleSet = userCache.getRoleSet(uid);
        return isAdmin(roleSet) || roleSet.contains(roleEnum.getRoleId());
    }

    @Override
    public boolean isAdmin(Set<Integer> roleSet) {
        return roleSet.contains(RoleEnum.ADMIN.getRoleId());
    }
}
