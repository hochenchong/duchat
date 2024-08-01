package hochenchong.duchat.common.common.service.cache;

import java.util.List;
import java.util.Map;

/**
 * @author hochenchong
 * @date 2024/08/01
 */
public interface BatchCache<I, O> {
    /**
     * 获取单个
     *
     * @param i 输入
     * @return 返回
     */
    O get(I i);

    /**
     * 批量获取
     *
     * @param iList 批量数据
     * @return 批量返回
     */
    Map<I, O> getBatch(List<I> iList);

    /**
     * 删除单个
     *
     * @param i 输入
     */
    void remove(I i);

    /**
     * 删除多个
     *
     * @param iList i列表
     */
    void removeAll(List<I> iList);
}
