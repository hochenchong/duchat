package hochenchong.duchat.common.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hochenchong.duchat.common.common.domain.vo.request.CursorPageBaseReq;
import hochenchong.duchat.common.common.domain.vo.response.CursorPageBaseResp;
import hochenchong.duchat.common.common.utils.CursorUtils;
import hochenchong.duchat.common.user.domain.entity.UserFriend;
import hochenchong.duchat.common.user.mapper.UserFriendMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户联系人表 服务实现类
 * </p>
 *
 * @author hochenchong
 * @since 2024-07-31
 */
@Service
public class UserFriendDao extends ServiceImpl<UserFriendMapper, UserFriend> {

    public UserFriend getByFriend(Long uid, Long targetUid) {
        return lambdaQuery().eq(UserFriend::getUid, uid).eq(UserFriend::getFriendUid, targetUid).one();
    }

    public List<UserFriend> getByFriends(Long uid, List<Long> uidList) {
        return lambdaQuery().eq(UserFriend::getUid, uid).in(UserFriend::getFriendUid, uidList).list();
    }

    public List<UserFriend> getFriendIds(Long uid, Long friendUid) {
        return lambdaQuery()
                .eq(UserFriend::getUid, uid)
                .eq(UserFriend::getFriendUid, friendUid)
                .or()
                .eq(UserFriend::getFriendUid, uid)
                .eq(UserFriend::getUid, friendUid)
                .select(UserFriend::getId)
                .list();
    }

    public CursorPageBaseResp<UserFriend> getFriendPage(Long uid, CursorPageBaseReq request) {
        return CursorUtils.getCursorPageByMysql(this, request, wrapper -> wrapper.eq(UserFriend::getUid, uid), UserFriend::getId);
    }
}
