package hochenchong.duchat.common.user.dao;

import hochenchong.duchat.common.user.domain.entity.User;
import hochenchong.duchat.common.user.mapper.UserMapper;
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
public class UserDao extends ServiceImpl<UserMapper, User> {

    /**
     * 通过用户昵称来查询用户
     *
     * @param nickname 用户昵称
     * @return 用户
     */
    public User getByNickname(String nickname) {
        return lambdaQuery().eq(User::getNickname, nickname).one();
    }

    /**
     * 通过 OpenId 来查询用户
     *
     * @param openId openId
     * @return 用户
     */
    public User getByOpenId(String openId) {
        return lambdaQuery().eq(User::getOpenId, openId).one();
    }

    /**
     * 修改用户昵称
     *
     * @param uid 用户 id
     * @param nickname 用户昵称
     * @return 成功与否
     */
    public boolean modifyNickname(Long uid, String nickname) {
        return lambdaUpdate().eq(User::getId, uid).set(User::getNickname, nickname).update();
    }
}
