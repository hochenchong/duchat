package hochenchong.duchat.common.user.service;

import hochenchong.duchat.common.user.domain.enums.RoleEnum;

import java.util.Set;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author hochenchong
 * @since 2024-07-30
 */
public interface RoleService {
    /**
     * 判断该用户 id 是否有某个角色
     *
     * @param uid 用户 id
     * @param roleEnum 角色
     * @return 是否拥有
     */
    boolean hasPower(Long uid, RoleEnum roleEnum);

    /**
     * 判断是否是超级管理员
     *
     * @param roleSet 用户角色列表
     * @return 是否是超级管理员
     */
    boolean isAdmin(Set<Integer> roleSet);
}
