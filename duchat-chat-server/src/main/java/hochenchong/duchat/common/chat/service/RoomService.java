package hochenchong.duchat.common.chat.service;

import hochenchong.duchat.common.chat.domain.entity.RoomFriend;

/**
 * 房间服务
 *
 * @author hochenchong
 * @date 2024/08/02
 */
public interface RoomService {
    /**
     * 创建私聊房间
     *
     * @param uid1 用户1
     * @param uid2 用户2
     * @return 私聊房间
     */
    RoomFriend createFriendRoom(Long uid1, Long uid2);

    /**
     * 获取两个用户的私聊房间
     *
     * @param uid1 用户1
     * @param uid2 用户2
     * @return 私聊房间
     */
    RoomFriend getFriendRoom(Long uid1, Long uid2);

    /**
     * 删除私聊房间
     *
     * @param uid1 用户1
     * @param uid2 用户2
     */
    void delFriendRoom(Long uid1, Long uid2);
}
