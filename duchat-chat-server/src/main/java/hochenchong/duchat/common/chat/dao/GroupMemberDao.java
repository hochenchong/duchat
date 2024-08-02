package hochenchong.duchat.common.chat.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hochenchong.duchat.common.chat.domain.entity.GroupMember;
import hochenchong.duchat.common.chat.mapper.GroupMemberMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 群成员表 服务实现类
 * </p>
 *
 * @author hochenchong
 * @since 2024-08-02
 */
@Service
public class GroupMemberDao extends ServiceImpl<GroupMemberMapper, GroupMember> {

}
