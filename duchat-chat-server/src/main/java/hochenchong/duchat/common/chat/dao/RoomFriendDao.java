package hochenchong.duchat.common.chat.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hochenchong.duchat.common.chat.domain.entity.RoomFriend;
import hochenchong.duchat.common.chat.mapper.RoomFriendMapper;
import hochenchong.duchat.common.common.domain.enums.TF;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 单聊房间表 服务实现类
 * </p>
 *
 * @author hochenchong
 * @since 2024-08-02
 */
@Service
public class RoomFriendDao extends ServiceImpl<RoomFriendMapper, RoomFriend> {

    public RoomFriend getByRoomId(Long roomId) {
        return lambdaQuery().eq(RoomFriend::getRoomId, roomId).one();
    }

    public RoomFriend getByKey(String key) {
        return lambdaQuery().eq(RoomFriend::getRoomKey, key).one();
    }

    public void restoreRoom(Long id) {
        lambdaUpdate()
                .eq(RoomFriend::getId, id)
                .set(RoomFriend::getDeleteStatus, TF.NO.getStatus())
                .update();
    }

    /**
     * 并非真的删除，设置状态禁用房间
     *
     * @param key 私聊房间
     */
    public void delFriendRoom(String key) {
        lambdaUpdate()
                .eq(RoomFriend::getRoomKey, key)
                .set(RoomFriend::getDeleteStatus, TF.YES.getStatus())
                .update();
    }
}
