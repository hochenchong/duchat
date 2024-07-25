package hochenchong.duchat.common.common.aspect;

import hochenchong.duchat.common.common.annotation.RedissonLock;
import hochenchong.duchat.common.common.service.LockService;
import hochenchong.duchat.common.utils.SpElUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 分布式锁切面
 *
 * @author hochenchong
 * @date 2024/07/25
 */
// 确保在事务注解前执行，分布式锁在事务外
@Order(0)
@Slf4j
@Aspect
@Component
public class RedissonLockAspect {
    @Autowired
    private LockService lockService;

    @Around("@annotation(hochenchong.duchat.common.common.annotation.RedissonLock)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        RedissonLock redissonLock = method.getAnnotation(RedissonLock.class);
        String prefix = StringUtils.isBlank(redissonLock.prefixKey()) ? SpElUtils.getMethodKey(method) : redissonLock.prefixKey();
        String key = SpElUtils.parseSpEl(method, joinPoint.getArgs(), redissonLock.key());
        return lockService.executeWithLock(prefix + ":" + key, redissonLock.waitTime(), redissonLock.unit(), joinPoint::proceed);
    }
}
