package hochenchong.duchat.common.user.service.impl;

import hochenchong.duchat.common.common.event.UserBlackEvent;
import hochenchong.duchat.common.common.event.UserRegisterEvent;
import hochenchong.duchat.common.common.exception.CustomErrorEnum;
import hochenchong.duchat.common.common.utils.AssertUtils;
import hochenchong.duchat.common.user.dao.BlackDao;
import hochenchong.duchat.common.user.dao.UserBackpackDao;
import hochenchong.duchat.common.user.dao.UserDao;
import hochenchong.duchat.common.user.domain.entity.Black;
import hochenchong.duchat.common.user.domain.entity.ItemConfig;
import hochenchong.duchat.common.user.domain.entity.User;
import hochenchong.duchat.common.user.domain.entity.UserBackpack;
import hochenchong.duchat.common.user.domain.enums.BlackTypeEnum;
import hochenchong.duchat.common.user.domain.enums.ItemEnum;
import hochenchong.duchat.common.user.domain.enums.ItemTypeEnum;
import hochenchong.duchat.common.user.domain.vo.req.BlackReq;
import hochenchong.duchat.common.user.domain.vo.resp.BadgeResp;
import hochenchong.duchat.common.user.domain.vo.resp.UserInfoResp;
import hochenchong.duchat.common.user.service.UserService;
import hochenchong.duchat.common.user.service.adapter.UserAdapter;
import hochenchong.duchat.common.user.service.cache.ItemCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;


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
    @Autowired
    private BlackDao blackDao;
    @Autowired
    private ItemCache itemCache;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public Long register(User user) {
        userDao.save(user);
        // 用户注册事件
        applicationEventPublisher.publishEvent(new UserRegisterEvent(this, user));
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

    @Override
    public List<BadgeResp> badges(Long uid) {
        // 获取所有徽章
        List<ItemConfig> itemConfigs = itemCache.getByType(ItemTypeEnum.BADGE.getType());
        // 查询用户拥有的徽章
        List<UserBackpack> userOwnBadges = userBackpackDao.getByItemIds(uid, itemConfigs.stream().map(ItemConfig::getId).toList());
        // 查询用户佩戴的徽章
        User user = userDao.getById(uid);
        return UserAdapter.buildBadgeResp(user, userOwnBadges, itemConfigs);
    }

    @Override
    public void wearingBadge(Long uid, int badgeId) {
        // 判断用户是否有该道具
        UserBackpack firstValidItem = userBackpackDao.getFirstValidItem(uid, badgeId);
        AssertUtils.isNotEmpty(firstValidItem, CustomErrorEnum.BADGE_ITEM_NOT_OWN);
        // 判断该道具是否是徽章
        ItemConfig itemConfig = itemCache.getById(firstValidItem.getItemId());
        AssertUtils.equal(itemConfig.getType(), ItemTypeEnum.BADGE.getType(), CustomErrorEnum.ITEM_TYPE_ERROR);
        // 佩戴徽章
        userDao.wearingBadge(uid, badgeId);
    }

    @Override
    public void black(BlackReq req) {
        Long uid = req.getUid();
        Black black = new Black();
        black.setType(BlackTypeEnum.UID.getType());
        black.setTarget(uid.toString());
        blackDao.save(black);
        // 拉黑该用户的 IP 地址
        User user = userDao.getById(uid);
        blackIp(user);
        // 发送拉黑事件
        applicationEventPublisher.publishEvent(new UserBlackEvent(this, user));
    }

    private void blackIp(User user) {
        if (user.getIpInfo() == null) {
            return;
        }
        String createIp = user.getIpInfo().getCreateIp();
        String updateIp = user.getIpInfo().getUpdateIp();
        blackIp(createIp);
        if (!Objects.equals(createIp, updateIp)) {
            blackIp(updateIp);
        }
    }

    private void blackIp(String ip) {
        if (StringUtils.isEmpty(ip)) {
            return;
        }
        Black black = new Black();
        black.setType(BlackTypeEnum.IP.getType());
        black.setTarget(ip);
        blackDao.save(black);
    }
}
