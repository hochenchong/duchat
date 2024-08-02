package hochenchong.duchat.common.chat.service.impl;

import hochenchong.duchat.common.chat.dao.RoomDao;
import hochenchong.duchat.common.chat.dao.RoomFriendDao;
import hochenchong.duchat.common.chat.domain.entity.Room;
import hochenchong.duchat.common.chat.domain.entity.RoomFriend;
import hochenchong.duchat.common.chat.domain.enums.RoomTypeEnum;
import hochenchong.duchat.common.chat.service.RoomService;
import hochenchong.duchat.common.chat.service.adapter.ChatAdapter;
import hochenchong.duchat.common.common.domain.enums.TF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 房间服务
 *
 * @author hochenchong
 * @date 2024/08/02
 */
@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomFriendDao roomFriendDao;
    @Autowired
    private RoomDao roomDao;

    @Override
    public RoomFriend createFriendRoom(Long uid1, Long uid2) {
        List<Long> uidList = Arrays.asList(uid1, uid2);
        String key = ChatAdapter.generateRoomKey(uidList);
        RoomFriend roomFriend = roomFriendDao.getByKey(key);
        // 如果之前有过，则直接恢复
        if (Objects.nonNull(roomFriend)) {
            restoreRoomIfNeed(roomFriend);
        } else {
            // 创建房间
            Room room = createRoom(RoomTypeEnum.FRIEND);
            roomFriend = createFriendRoom(room.getId(), uidList);
        }
        return roomFriend;
    }

    @Override
    public RoomFriend getFriendRoom(Long uid1, Long uid2) {
        String key = ChatAdapter.generateRoomKey(Arrays.asList(uid1, uid2));
        return roomFriendDao.getByKey(key);
    }

    @Override
    public void delFriendRoom(Long uid1, Long uid2) {
        String key = ChatAdapter.generateRoomKey(Arrays.asList(uid1, uid2));
        roomFriendDao.delFriendRoom(key);
    }

    private RoomFriend createFriendRoom(Long roomId, List<Long> uidList) {
        RoomFriend roomFriend = ChatAdapter.buildFriendRoom(roomId, uidList);
        roomFriendDao.save(roomFriend);
        return roomFriend;
    }

    private Room createRoom(RoomTypeEnum typeEnum) {
        Room room = new Room();
        room.setType(typeEnum.getType());
        roomDao.save(room);
        return room;
    }

    private void restoreRoomIfNeed(RoomFriend room) {
        if (Objects.equals(room.getDeleteStatus(), TF.YES.getStatus())) {
            roomFriendDao.restoreRoom(room.getId());
        }
    }
}
