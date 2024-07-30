package hochenchong.duchat.common.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hochenchong.duchat.common.user.domain.entity.Black;
import hochenchong.duchat.common.user.mapper.BlackMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 黑名单 服务实现类
 * </p>
 *
 * @author hochenchong
 * @since 2024-07-30
 */
@Service
public class BlackDao extends ServiceImpl<BlackMapper, Black> {

}
