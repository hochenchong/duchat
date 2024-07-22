package hochenchong.duchat.common.user.service;

/**
 * @author hochenchong
 * @date 2024/07/15
 */
public interface LoginService {
    /**
     * 校验 token 是不是有效
     *
     * @param token token信息
     * @return 是否有效
     */
    boolean verify(String token);

    /**
     * 刷新 token 有效期
     *
     * @param token token信息
     */
    void renewalTokenIfNecessary(String token);

    /**
     * 登录成功，获取 token
     *
     * @param uid 用户id
     * @return 返回token
     */
    String login(Long uid);

    /**
     * 如果 token 有效，返回 uid
     *
     * @param token token信息
     * @return 用户id
     */
    Long getValidUid(String token);
}