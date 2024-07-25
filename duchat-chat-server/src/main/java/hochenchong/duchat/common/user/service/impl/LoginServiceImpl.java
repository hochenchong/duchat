package hochenchong.duchat.common.user.service.impl;

import hochenchong.duchat.common.common.constant.RedisKey;
import hochenchong.duchat.common.common.utils.JwtUtils;
import hochenchong.duchat.common.common.utils.PasswordUtil;
import hochenchong.duchat.common.user.dao.UserDao;
import hochenchong.duchat.common.user.domain.entity.User;
import hochenchong.duchat.common.user.service.LoginService;
import hochenchong.duchat.common.utils.RedisUtils;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 登录服务
 *
 * @author hochenchong
 * @date 2024/07/15
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private JwtUtils jwtUtils;

    @Resource
    private UserDao userDao;

    public static final int TOKEN_EXPIRE_DAYS = 3;
    public static final int TOKEN_RENEWAL_DAYS = 1;

    @Override
    public User userLogin(String name, String password) {
        User user = userDao.getByNickname(name);
        if (user == null) {
            return null;
        }
        // 校验密码，如果通过，则返回用户
        if (PasswordUtil.checkPassword(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public boolean verify(String token) {
        Long uid = jwtUtils.getUidOrNull(token);
        if (Objects.isNull(uid)) {
            return false;
        }
        // 有可能 token 已经失效了，需要校验和 redis 里的最新 token 是否一致
        String realToken = RedisUtils.getStr(RedisKey.getKey(RedisKey.USER_TOKEN_STRING, uid));
        return Objects.equals(token, realToken);
    }

    /**
     * 如果需要的话刷新token有效期
     *
     * @param token token信息
     */
    @Override
    @Async
    public void renewalTokenIfNecessary(String token) {
        Long uid = getValidUid(token);
        if (Objects.isNull(uid)) {
            return;
        }
        String userTokenKey = getUserTokenKey(uid);
        // 获取有效期
        Long expireDays = RedisUtils.getExpire(userTokenKey, TimeUnit.DAYS);
        if (expireDays == -2) {
            // 如果不存在，则直接返回
            return;
        }
        if (expireDays < TOKEN_RENEWAL_DAYS) {
            RedisUtils.expire(userTokenKey, TOKEN_EXPIRE_DAYS, TimeUnit.DAYS);
        }
    }

    /**
     * 用户登录后，存入 uid 与 token 的关系，并返回 token
     *
     * @param uid 用户id
     * @return 返回 token
     */
    @Override
    public String login(Long uid) {
        String token = jwtUtils.createToken(uid);
        RedisUtils.set(getUserTokenKey(uid), token, TOKEN_EXPIRE_DAYS, TimeUnit.DAYS);
        return token;
    }

    /**
     * 根据传入的 token 解析用户id
     *
     * @param token token信息
     * @return 用户id
     */
    @Override
    public Long getValidUid(String token) {
        Long uid = jwtUtils.getUidOrNull(token);
        if (Objects.isNull(uid)) {
            return null;
        }
        String oldToken = RedisUtils.getStr(getUserTokenKey(uid));
        if (StringUtils.isBlank(oldToken)) {
            return null;
        }
        return Objects.equals(oldToken, token) ? uid : null;
    }

    private static String getUserTokenKey(Long uid) {
        return RedisKey.getKey(RedisKey.USER_TOKEN_STRING, uid);
    }
}
