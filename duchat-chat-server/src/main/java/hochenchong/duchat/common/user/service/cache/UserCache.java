package hochenchong.duchat.common.user.service.cache;

import hochenchong.duchat.common.user.dao.BlackDao;
import hochenchong.duchat.common.user.dao.UserRoleDao;
import hochenchong.duchat.common.user.domain.entity.Black;
import hochenchong.duchat.common.user.domain.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
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
    @Autowired
    private BlackDao blackDao;

    @Cacheable(value = "user", key = "'roles:'+#uid")
    public Set<Integer> getRoleSet(Long uid) {
        List<UserRole> userRoles = userRoleDao.listByUid(uid);
        return userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toSet());
    }

    @Cacheable(value = "user", key = "'blackList'")
    public Map<Integer, Set<String>> getBlackMap() {
        return blackDao.list().stream().collect(Collectors.groupingBy(Black::getType,
                Collectors.mapping(Black::getTarget, Collectors.toSet())));
    }

    @CacheEvict(value = "user", key = "'blackList'")
    public void evictBlackMap() {
    }
}
