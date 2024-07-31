package hochenchong.duchat.common.user.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hochenchong.duchat.common.user.domain.entity.UserApply;
import hochenchong.duchat.common.user.domain.enums.ApplyReadStatusEnum;
import hochenchong.duchat.common.user.domain.enums.ApplyStatusEnum;
import hochenchong.duchat.common.user.domain.enums.ApplyTypeEnum;
import hochenchong.duchat.common.user.mapper.UserApplyMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户申请表 服务实现类
 * </p>
 *
 * @author hochenchong
 * @since 2024-07-31
 */
@Service
public class UserApplyDao extends ServiceImpl<UserApplyMapper, UserApply> {

    public UserApply getFriendApply(Long uid, Long targetId) {
        return lambdaQuery().eq(UserApply::getUid, uid)
                .eq(UserApply::getTargetId, targetId)
                .eq(UserApply::getStatus, ApplyStatusEnum.PENDING)
                .eq(UserApply::getType, ApplyTypeEnum.ADD_FRIEND)
                .one();
    }

    public void agree(Long applyId) {
        lambdaUpdate().eq(UserApply::getId, applyId)
                .set(UserApply::getStatus, ApplyStatusEnum.AGREE.getCode())
                .set(UserApply::getReadStatus, ApplyReadStatusEnum.READ.getCode())
                .update();
    }

    public Page<UserApply> friendApplyPage(Long uid, Page page) {
        return lambdaQuery()
                .eq(UserApply::getTargetId, uid)
                .eq(UserApply::getType, ApplyTypeEnum.ADD_FRIEND.getCode())
                .eq(UserApply::getStatus, ApplyStatusEnum.PENDING.getCode())
                .orderByDesc(UserApply::getCreateTime)
                .page(page);
    }

    public void readApply(Long uid, List<Long> applyIds) {
        lambdaUpdate()
                .set(UserApply::getReadStatus, ApplyReadStatusEnum.READ.getCode())
                .eq(UserApply::getReadStatus, ApplyReadStatusEnum.UNREAD.getCode())
                .eq(UserApply::getTargetId, uid)
                .in(UserApply::getId, applyIds)
                .update();
    }

    /**
     * 获取用户未读好友申请数，上限 99
     *
     * @param uid 用户 id
     * @return 未读好友申请数，最多 99
     */
    public int getUnReadCount(Long uid) {
        return Math.toIntExact(lambdaQuery().eq(UserApply::getTargetId, uid)
                .eq(UserApply::getReadStatus, ApplyReadStatusEnum.UNREAD.getCode())
                .last("limit 99")
                .count());
    }
}
