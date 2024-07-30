package hochenchong.duchat.common.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hochenchong.duchat.common.user.domain.entity.Role;
import hochenchong.duchat.common.user.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author hochenchong
 * @since 2024-07-30
 */
@Service
public class RoleDao extends ServiceImpl<RoleMapper, Role> {

}
