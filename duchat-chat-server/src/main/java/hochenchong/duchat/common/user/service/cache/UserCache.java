package hochenchong.duchat.common.user.service.cache;

import hochenchong.duchat.common.user.dao.UserRoleDao;
import hochenchong.duchat.common.user.domain.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户角色相关缓存
 *
 * @author hochenchong
 * @date 2024/07/30
 */
@Component
public class UserCache {
    @Autowired
    private UserRoleDao userRoleDao;

    @Cacheable(value = "user", key = "'roles:'+#uid")
    public Set<Integer> getRoleSet(Long uid) {
        List<UserRole> userRoles = userRoleDao.listByUid(uid);
        return userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toSet());
    }
}
