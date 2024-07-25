package hochenchong.duchat.common.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author hochenchong
 * @date 2024/07/25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RedissonLock {
    /**
     * key 的前缀
     *     默认取方法全限定名
     *     可以自己指定
     *
     * @return key 的前缀
     */
    String prefixKey() default "";

    /**
     * key
     *     支持 springEl 表达式
     *
     * @return key
     */
    String key();

    /**
     * 等待锁的时间
     *     默认 -1
     *     不等待直接失败,redisson 默认也是 -1
     *
     * @return 等待锁的时间
     */
    int waitTime() default -1;

    /**
     * 等待锁的时间单位，默认毫秒
     *
     * @return 单位
     */
    TimeUnit unit() default TimeUnit.MILLISECONDS;

}