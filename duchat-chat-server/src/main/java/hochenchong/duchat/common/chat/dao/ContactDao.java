package hochenchong.duchat.common.chat.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hochenchong.duchat.common.chat.domain.entity.Contact;
import hochenchong.duchat.common.chat.mapper.ContactMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会话列表 服务实现类
 * </p>
 *
 * @author hochenchong
 * @since 2024-08-02
 */
@Service
public class ContactDao extends ServiceImpl<ContactMapper, Contact> {

}
