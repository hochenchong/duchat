package hochenchong.duchat.common.common.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 频控注解
 *
 * @author hochenchong
 * @date 2024/08/28
 */
@Repeatable(FrequencyControlContainer.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FrequencyControl {
    /**
     * key 的前缀，默认取方法全限定名，除非在不同方法上对同一资源做频控，就自己指定
     *
     * @return key 的前缀
     */
    String prefixKey() default "";

    /**
     * 频控对象，默认 el 表达指定具体的频控对象
     * 对 IP 和 uid 模式，需要是 http 入口的对象，保证 RequestHolder 里有值
     *
     * @return 对象
     */
    Target target() default Target.EL;

    /**
     * Spring EL 表达式，target 为 EL 时生效
     *
     * @return el 表达式
     */
    String spEl() default "";

    /**
     * 频控时间
     *
     * @return 时间
     */
    int time();

    /**
     * 频控时间单位，默认秒
     *
     * @return 频控时间单位
     */
    TimeUnit unit() default TimeUnit.SECONDS;

    /**
     * 单位时间内最大次数限制
     *
     * @return 次数
     */
    int count();

    enum Target {
        UID, IP, EL
    }
}
