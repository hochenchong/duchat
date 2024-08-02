package hochenchong.duchat.common.chat.service.cache;

import hochenchong.duchat.common.chat.dao.RoomDao;
import hochenchong.duchat.common.chat.domain.entity.Room;
import hochenchong.duchat.common.common.constant.RedisKey;
import hochenchong.duchat.common.common.service.cache.AbstractRedisStringCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 房间缓存
 *
 * @author hochenchong
 * @date 2024/08/02
 */
@Component
public class RoomCache extends AbstractRedisStringCache<Long, Room> {
    @Autowired
    private RoomDao roomDao;

    @Override
    protected String getKey(Long roomId) {
        return RedisKey.getKey(RedisKey.ROOM_INFO_STRING, roomId);
    }

    @Override
    protected Long getExpireSeconds() {
        return 30 * 60L;
    }

    @Override
    protected Map<Long, Room> load(List<Long> roomIds) {
        List<Room> rooms = roomDao.listByIds(roomIds);
        return rooms.stream().collect(Collectors.toMap(Room::getId, Function.identity()));
    }
}
