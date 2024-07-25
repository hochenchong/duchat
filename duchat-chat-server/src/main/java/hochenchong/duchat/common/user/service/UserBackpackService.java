package hochenchong.duchat.common.user.service;

import hochenchong.duchat.common.user.domain.enums.IdempotentEnum;

/**
 * 用户背包服务
 *
 * @author hochenchong
 * @date 2024/07/25
 */
public interface UserBackpackService {

    /**
     * 发放道具
     *
     * @param uid 用户 id
     * @param itemId 道具 id
     * @param idempotentEnum 幂等类型
     * @param businessId 幂等唯一标识
     */
    void acquireItem(Long uid, int itemId, IdempotentEnum idempotentEnum, String businessId);
}
