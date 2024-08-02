package hochenchong.duchat.common.chat.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hochenchong.duchat.common.chat.domain.entity.Message;
import hochenchong.duchat.common.chat.mapper.MessageMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author hochenchong
 * @since 2024-08-02
 */
@Service
public class MessageDao extends ServiceImpl<MessageMapper, Message> {

}
