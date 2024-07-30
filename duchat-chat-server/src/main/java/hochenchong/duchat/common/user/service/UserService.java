package hochenchong.duchat.common.user.service;

import hochenchong.duchat.common.user.domain.entity.User;
import hochenchong.duchat.common.user.domain.vo.req.BlackReq;
import hochenchong.duchat.common.user.domain.vo.resp.BadgeResp;
import hochenchong.duchat.common.user.domain.vo.resp.UserInfoResp;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author hochenchong
 * @since 2024-03-19
 */
public interface UserService {
    /**
     * 创建用户，返回用户 id
     */
    Long register(User user);

    /**
     * 获取用户信息
     *
     * @param uid 用户 id
     * @return 用户信息
     */
    UserInfoResp getUserInfo(Long uid);

    /**
     * 修改用户昵称
     *
     * @param uid 用户 id
     * @param nickname 用户名
     */
    void modifyNickname(Long uid, String nickname);

    /**
     * 用户徽章列表
     *
     * @param uid 用户 id
     * @return 用户徽章列表
     */
    List<BadgeResp> badges(Long uid);

    /**
     * 用户佩戴徽章
     *
     * @param uid 用户 id
     * @param badgeId 要佩戴的徽章
     */
    void wearingBadge(Long uid, int badgeId);

    /**
     * 拉黑用户
     *
     * @param req 被拉黑用户的信息
     */
    void black(BlackReq req);
}
