package hochenchong.duchat.common.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 多策略的容器注解
 *
 * @author hochenchong
 * @date 2024/08/28
 */
// 运行时生效
@Retention(RetentionPolicy.RUNTIME)
// 作用在方法上
@Target(ElementType.METHOD)
public @interface FrequencyControlContainer {
    FrequencyControl[] value();
}
