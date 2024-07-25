package hochenchong.duchat.common.user.service.cache;

import hochenchong.duchat.common.user.dao.ItemConfigDao;
import hochenchong.duchat.common.user.domain.entity.ItemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 道具相关缓存
 *
 * @author hochenchong
 * @date 2024/07/25
 */
@Component
public class ItemCache {
    @Autowired
    private ItemConfigDao itemConfigDao;

    @Cacheable(value = "item", key = "'itemsByType:'+#itemType")
    public List<ItemConfig> getByType(int itemType) {
        return itemConfigDao.getByType(itemType);
    }

    @Cacheable(value = "item", key = "'itemsById:'+#itemId")
    public ItemConfig getById(Integer itemId) {
        return itemConfigDao.getById(itemId);
    }
}
