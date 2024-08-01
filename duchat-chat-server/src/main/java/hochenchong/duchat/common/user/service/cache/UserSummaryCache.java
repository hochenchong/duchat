package hochenchong.duchat.common.user.service.cache;

import hochenchong.duchat.common.common.constant.RedisKey;
import hochenchong.duchat.common.common.service.cache.AbstractRedisStringCache;
import hochenchong.duchat.common.user.dao.UserDao;
import hochenchong.duchat.common.user.domain.dto.SummeryInfoDTO;
import hochenchong.duchat.common.user.domain.entity.IpDetail;
import hochenchong.duchat.common.user.domain.entity.IpInfo;
import hochenchong.duchat.common.user.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 用户信息缓存
 *
 * @author hochenchong
 * @date 2024/08/01
 */
@Component
public class UserSummaryCache extends AbstractRedisStringCache<Long, SummeryInfoDTO> {
    @Autowired
    private UserDao userDao;

    @Override
    protected String getKey(Long uid) {
        return RedisKey.getKey(RedisKey.USER_SUMMARY_STRING, uid);
    }

    @Override
    protected Long getExpireSeconds() {
        return 30 * 60L;
    }

    @Override
    protected Map<Long, SummeryInfoDTO> load(List<Long> uidList) {
        List<User> needLoadUserList = userDao.listByIds(uidList);
        return needLoadUserList.stream().map(u -> SummeryInfoDTO.builder()
                .uid(u.getId())
                .name(u.getNickname())
                .avatar(u.getAvatar())
                .region(Optional.ofNullable(u.getIpInfo()).map(IpInfo::getUpdateIpDetail).map(IpDetail::getCity).orElse(null))
                .itemId(u.getItemId())
                .build()).collect(Collectors.toMap(SummeryInfoDTO::getUid, Function.identity()));
    }
}
