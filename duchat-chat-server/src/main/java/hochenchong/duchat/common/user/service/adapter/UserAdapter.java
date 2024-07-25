package hochenchong.duchat.common.user.service.adapter;

import hochenchong.duchat.common.user.domain.entity.User;
import hochenchong.duchat.common.user.domain.vo.resp.UserInfoResp;
import org.springframework.beans.BeanUtils;

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
}
