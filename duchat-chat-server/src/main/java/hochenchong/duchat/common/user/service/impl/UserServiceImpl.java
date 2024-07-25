package hochenchong.duchat.common.user.service.impl;

import hochenchong.duchat.common.common.exception.CustomErrorEnum;
import hochenchong.duchat.common.common.utils.AssertUtils;
import hochenchong.duchat.common.user.dao.UserBackpackDao;
import hochenchong.duchat.common.user.dao.UserDao;
import hochenchong.duchat.common.user.domain.entity.User;
import hochenchong.duchat.common.user.domain.entity.UserBackpack;
import hochenchong.duchat.common.user.domain.enums.ItemEnum;
import hochenchong.duchat.common.user.domain.vo.resp.UserInfoResp;
import hochenchong.duchat.common.user.service.UserService;
import hochenchong.duchat.common.user.service.adapter.UserAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 用户服务
 *
 * @author hochenchong
 * @date 2024/07/24
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserBackpackDao userBackpackDao;

    @Override
    public Long register(User user) {
        userDao.save(user);
        return user.getId();
    }

    @Override
    public UserInfoResp getUserInfo(Long uid) {
        User user = userDao.getById(uid);
        int modifyNameCount = userBackpackDao.getCountByValidItemId(uid, ItemEnum.MODIFY_NAME_CARD.getId());
        return UserAdapter.buildUserInfo(user, modifyNameCount);
    }

    @Override
    @Transactional
    public void modifyNickname(Long uid, String nickname) {
        User oldUser = userDao.getByNickname(nickname);
        AssertUtils.isEmpty(oldUser, CustomErrorEnum.NAME_ALREADY_EXISTS);
        UserBackpack modifyNameItem = userBackpackDao.getFirstValidItem(uid, ItemEnum.MODIFY_NAME_CARD.getId());
        AssertUtils.isNotEmpty(modifyNameItem, CustomErrorEnum.MODIFY_NAME_ITEM_NOT_ENOUGH);
        if (userBackpackDao.userItem(modifyNameItem)) {
            userDao.modifyNickname(uid, nickname);
        }
    }
}
