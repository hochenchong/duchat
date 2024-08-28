package hochenchong.duchat.common.common.aspect;

import hochenchong.duchat.common.common.annotation.FrequencyControl;
import hochenchong.duchat.common.common.exception.CommonErrorEnum;
import hochenchong.duchat.common.common.exception.CustomException;
import hochenchong.duchat.common.common.utils.RequestHolder;
import hochenchong.duchat.common.utils.RedisUtils;
import hochenchong.duchat.common.utils.SpElUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 频控切面
 *     固定时间窗口 / 滑动窗口
 *
 * @author hochenchong
 * @date 2024/08/28
 */
@Slf4j
@Aspect
@Component
public class FrequencyControlAspect {

    @Around("@annotation(hochenchong.duchat.common.common.annotation.FrequencyControl) || @annotation(hochenchong.duchat.common.common.annotation.FrequencyControlContainer)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        FrequencyControl[] annotationsByType = method.getAnnotationsByType(FrequencyControl.class);
        Map<String, FrequencyControl> keyMap = new HashMap<>();
        // 遍历频控注解，获取 key
        for (int i = 0; i < annotationsByType.length; i++) {
            FrequencyControl frequencyControl = annotationsByType[i];
            // 默认前缀为方法限定名+注解顺序
            String prefix = StringUtils.isBlank(frequencyControl.prefixKey()) ? SpElUtils.getMethodKey(method) + ":index:" + i : frequencyControl.prefixKey();
            String key = switch (frequencyControl.target()) {
                case EL -> SpElUtils.parseSpEl(method, joinPoint.getArgs(), frequencyControl.spEl());
                case IP -> RequestHolder.get().getIp();
                case UID -> RequestHolder.get().getUid().toString();
            };
            keyMap.put(prefix + ":" + key, frequencyControl);
        }

        // 固定时间窗口
        boolean allowed = isAllowedFixedWindow(keyMap);
        // 滑动窗口
        // boolean allowed = isAllowedSlidingWindow(keyMap);

        if (allowed) {
            return joinPoint.proceed();
        } else {
            throw new CustomException(CommonErrorEnum.FREQUENCY_LIMIT);
        }
    }

    /**
     * 固定时间窗口 (Fixed Window)
     *
     * @param keyMap KEY map
     * @return 是否通过
     */
    private static boolean isAllowedFixedWindow(Map<String, FrequencyControl> keyMap) {
        boolean flag = true;
        // 批量获取 redis 统计的值
        List<String> keyList = keyMap.keySet().stream().toList();
        List<Integer> countList = RedisUtils.mget(keyList, Integer.class);
        // 按顺序检测 key 是否达到限制
        for (int i = 0; i < keyList.size(); i++) {
            String key = keyList.get(i);
            Integer count = countList.get(i);
            FrequencyControl frequencyControl = keyMap.get(key);
            if (Objects.nonNull(count) && count >= frequencyControl.count()) {
                log.warn("frequencyControl limit key:{}, count:{}", key, count);
                flag = false;
            }
            RedisUtils.inc(key, frequencyControl.time(), frequencyControl.unit());
        }
        return flag;
    }

    /**
     * 滑动时间窗口 (Fixed Window)
     *
     * @param keyMap KEY map
     * @return 是否通过
     */
    private static boolean isAllowedSlidingWindow(Map<String, FrequencyControl> keyMap) {
        boolean flag = true;
        long now = Instant.now().getEpochSecond();
        for (String key : keyMap.keySet()) {
            FrequencyControl frequencyControl = keyMap.get(key);
            // 将时间转换为 秒
            long windowInSeconds = frequencyControl.unit().toSeconds(frequencyControl.time());

            // 移除窗口外的请求
            RedisUtils.zRemoveRangeByScore(key, 0, now - windowInSeconds);
            // 统计当前窗口内的请求数
            Long count = RedisUtils.zSize(key);
            if (Objects.nonNull(count) && count >= frequencyControl.count()) {
                log.warn("frequencyControl limit key:{}, count:{}", key, count);
                flag = false;
            }
            // 添加当前请求的时间戳
            RedisUtils.zAdd(key, String.valueOf(now), now);
            // 设置键的过期时间为窗口时间的两倍，确保旧数据可以被清理
            RedisUtils.expire(key, frequencyControl.time() * 2L, frequencyControl.unit());
        }
        return flag;
    }
}
