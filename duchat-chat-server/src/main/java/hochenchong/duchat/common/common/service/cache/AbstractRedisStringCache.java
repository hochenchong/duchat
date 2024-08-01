package hochenchong.duchat.common.common.service.cache;

import hochenchong.duchat.common.utils.RedisUtils;
import org.springframework.core.ResolvableType;
import org.springframework.util.CollectionUtils;
import java.util.*;

/**
 * 抽象 Redis String 缓存
 *
 * @author hochenchong
 * @date 2024/08/01
 */
public abstract class AbstractRedisStringCache<I, O> implements BatchCache<I, O> {
    private final Class<O> oClass;

    protected AbstractRedisStringCache() {
        /* 通过反射的方式获取
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.oClass = (Class<O>) genericSuperclass.getActualTypeArguments()[1];
        */

        /* Guava 库中的 TypeToken 可以优雅地获取泛型类型参数
        TypeToken<O> typeToken = new TypeToken<O>(getClass()) {};
        this.oClass = (Class<O>) typeToken.getRawType();
         */

        // 使用 Spring 框架的工具获取
        ResolvableType resolvableType = ResolvableType.forClass(getClass());
        this.oClass = (Class<O>) resolvableType.getSuperType().getGeneric(1).resolve();
    }

    protected abstract String getKey(I i);

    protected abstract Long getExpireSeconds();

    protected abstract Map<I, O> load(List<I> iList);

    @Override
    public O get(I req) {
        return getBatch(Collections.singletonList(req)).get(req);
    }

    @Override
    public Map<I, O> getBatch(List<I> iList) {
        // iList.isEmpty() 在 ArrayList 里，直接判断 size 是否为 0，如果为空，则会有异常
        if (CollectionUtils.isEmpty(iList)) {
            return new HashMap<>();
        }
        // 去重
        iList = iList.stream().distinct().toList();
        // 组装 key
        List<String> keys = iList.stream().map(this::getKey).toList();
        // 批量 get
        List<O> valueList = RedisUtils.mget(keys, oClass);
        // 返回的 Map
        Map<I, O> resultMap = new HashMap<>();

        // 需要加载的列表
        List<I> needloadList = new ArrayList<>();
        for (int i = 0; i < valueList.size(); i++) {
            // 为空则需要查询，否则放入返回的 Map 之中
            if (Objects.isNull(valueList.get(i))) {
                needloadList.add(iList.get(i));
            } else {
                resultMap.put(iList.get(i), valueList.get(i));
            }
        }

        // 如果没有需要加载的列表，即 redis 查出来了全部，直接返回即可
        if (CollectionUtils.isEmpty(needloadList)) {
            return resultMap;
        }

        // 查询缺失的数据
        Map<I, O> load = load(needloadList);
        Map<String, O> loadMap = new HashMap<>();
        // 获取 key，存入 redis
        load.forEach((k, v) -> loadMap.put(getKey(k), v));
        RedisUtils.mset(loadMap, getExpireSeconds());
        // 放入返回的 Map 之中
        resultMap.putAll(load);
        return resultMap;
    }

    @Override
    public void remove(I i) {
        removeAll(Collections.singletonList(i));
    }

    @Override
    public void removeAll(List<I> iList) {
        List<String> keys = iList.stream().map(this::getKey).toList();
        RedisUtils.del(keys);
    }
}
