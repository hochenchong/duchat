package hochenchong.duchat.common.chat.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hochenchong.duchat.common.chat.domain.entity.Room;
import hochenchong.duchat.common.chat.mapper.RoomMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 房间表 服务实现类
 * </p>
 *
 * @author hochenchong
 * @since 2024-08-02
 */
@Service
public class RoomDao extends ServiceImpl<RoomMapper, Room> {

}
