package hochenchong.duchat.common.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hochenchong.duchat.common.user.domain.entity.ItemConfig;
import hochenchong.duchat.common.user.mapper.ItemConfigMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 道具配置表 服务实现类
 * </p>
 *
 * @author hochenchong
 * @since 2024-07-24
 */
@Service
public class ItemConfigDao extends ServiceImpl<ItemConfigMapper, ItemConfig> {

    /**
     * 根据道具类型获取道具配置列表
     *
     * @param itemType 道具类型
     * @return 道具配置列表
     */
    public List<ItemConfig> getByType(int itemType) {
        return lambdaQuery().eq(ItemConfig::getType, itemType).list();
    }
}
