package hochenchong.duchat.common.chat.service.adapter;

import hochenchong.duchat.common.chat.domain.entity.RoomFriend;
import hochenchong.duchat.common.common.domain.enums.TF;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hochenchong
 * @date 2024/08/02
 */
public class ChatAdapter {
    public static final String SEPARATOR = ",";

    public static String generateRoomKey(List<Long> uidList) {
        return uidList.stream()
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining(SEPARATOR));
    }

    public static RoomFriend buildFriendRoom(Long roomId, List<Long> uidList) {
        List<Long> collect = uidList.stream().sorted().toList();
        RoomFriend roomFriend = new RoomFriend();
        roomFriend.setRoomId(roomId);
        roomFriend.setUid1(collect.get(0));
        roomFriend.setUid2(collect.get(1));
        roomFriend.setRoomKey(generateRoomKey(uidList));
        roomFriend.setDeleteStatus(TF.NO.getStatus());
        return roomFriend;
    }
}
