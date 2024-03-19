package hochenchong.duchat.common.user.dao;

import hochenchong.duchat.common.user.domain.entity.User;
import hochenchong.duchat.common.user.mapper.UserMapper;
import hochenchong.duchat.common.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author hochenchong
 * @since 2024-03-19
 */
@Service
public class UserDao extends ServiceImpl<UserMapper, User> implements IUserService {

}
