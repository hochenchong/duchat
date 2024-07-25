package hochenchong.duchat.common.common.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import java.util.concurrent.TimeUnit;

/**
 * 缓存管理器
 *     CachingConfigurerSupport 类在 Spring 6 版本废弃了
 *     CachingConfigurerSupport 类实现了 CachingConfigurer 接口，提供了默认的空方法
 *     空方法已挪到 CachingConfigurer 接口中，直接实现 CachingConfigurer 接口即可
 *
 * @author hochenchong
 * @date 2024/07/25
 */
@Configuration
@EnableCaching
public class CacheConfig implements CachingConfigurer {

    /**
     * 使用 Caffeine 做缓存管理器
     *
     * @return 缓存管理器
     */
    @Bean("caffeineCacheManager")
    @Primary
    @Override
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .initialCapacity(100)
                .maximumSize(200));
        return cacheManager;
    }
}
