package hochenchong.duchat.common.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hochenchong.duchat.common.user.domain.entity.UserRole;
import hochenchong.duchat.common.user.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户角色关系表 服务实现类
 * </p>
 *
 * @author hochenchong
 * @since 2024-07-30
 */
@Service
public class UserRoleDao extends ServiceImpl<UserRoleMapper, UserRole> {

    /**
     * 根据用户 id 获取该用户的角色
     *
     * @param uid 用户 id
     * @return 用户角色关系
     */
    public List<UserRole> listByUid(Long uid) {
        return lambdaQuery()
                .eq(UserRole::getUid, uid)
                .list();
    }
}
