package hochenchong.duchat.common.user.service.adapter;

import hochenchong.duchat.common.common.domain.enums.TF;
import hochenchong.duchat.common.user.domain.entity.ItemConfig;
import hochenchong.duchat.common.user.domain.entity.User;
import hochenchong.duchat.common.user.domain.entity.UserBackpack;
import hochenchong.duchat.common.user.domain.vo.resp.user.BadgeResp;
import hochenchong.duchat.common.user.domain.vo.resp.user.UserInfoResp;
import org.springframework.beans.BeanUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author hochenchong
 * @date 2024/07/25
 */
public class UserAdapter {
    public static UserInfoResp buildUserInfo(User user, int modifyNameCount) {
        UserInfoResp vo = new UserInfoResp();
        BeanUtils.copyProperties(user, vo);
        vo.setModifyNameChance(modifyNameCount);
        return vo;
    }

    public static List<BadgeResp> buildBadgeResp(User user, List<UserBackpack> userOwnBadges, List<ItemConfig> itemConfigs) {
        Set<Integer> ownBadgeSet = userOwnBadges.stream().map(UserBackpack::getItemId).collect(Collectors.toSet());
        return itemConfigs.stream().map(i -> {
            BadgeResp resp = new BadgeResp();
            BeanUtils.copyProperties(i, resp);
            resp.setOwnStatus(ownBadgeSet.contains(i.getId()) ? TF.YES.getStatus() : TF.NO.getStatus());
            resp.setWearStatus(Objects.equals(i.getId(), user.getItemId()) ? TF.YES.getStatus() : TF.NO.getStatus());
            return resp;
        }).sorted(Comparator.comparing(BadgeResp::getWearStatus, Comparator.reverseOrder())
                .thenComparing(BadgeResp::getOwnStatus, Comparator.reverseOrder())
        ).toList();
    }

}
