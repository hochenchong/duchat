package hochenchong.duchat.common.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hochenchong.duchat.common.common.domain.enums.TF;
import hochenchong.duchat.common.user.domain.entity.UserBackpack;
import hochenchong.duchat.common.user.mapper.UserBackpackMapper;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <p>
 * 用户背包表 服务实现类
 * </p>
 *
 * @author hochenchong
 * @since 2024-07-24
 */
@Service
public class UserBackpackDao extends ServiceImpl<UserBackpackMapper, UserBackpack> {

    /**
     * 获取用户某个道具的数量
     *
     * @param uid 用户 id
     * @param itemId 道具 id
     * @return 道具数量
     */
    public int getCountByValidItemId(Long uid, int itemId) {
        return Math.toIntExact(lambdaQuery()
                .eq(UserBackpack::getUid, uid)
                .eq(UserBackpack::getItemId, itemId)
                .eq(UserBackpack::getStatus, TF.NO.getStatus())
                .count());
    }

    public UserBackpack getFirstValidItem(Long uid, int itemId) {
        return lambdaQuery()
                .eq(UserBackpack::getUid, uid)
                .eq(UserBackpack::getItemId, itemId)
                .eq(UserBackpack::getStatus, TF.NO.getStatus())
                .orderByAsc(UserBackpack::getId)
                .last("limit 1")
                .one();
    }

    public boolean userItem(UserBackpack userBackpack) {
        return lambdaUpdate()
                .eq(UserBackpack::getId, userBackpack.getId())
                .eq(UserBackpack::getStatus, TF.NO.getStatus())
                .set(UserBackpack::getStatus, TF.YES.getStatus())
                .update();
    }

    public List<UserBackpack> getByItemIds(Long uid, List<Integer> itemIds) {
        return lambdaQuery()
                .eq(UserBackpack::getUid, uid)
                .eq(UserBackpack::getStatus, TF.NO.getStatus())
                .in(UserBackpack::getItemId, itemIds)
                .list();
    }

    public UserBackpack getByIdempotent(String idempotent) {
        return lambdaQuery()
                .eq(UserBackpack::getIdempotent, idempotent)
                .one();
    }
}
