package hochenchong.duchat.common.common.service;

import hochenchong.duchat.common.common.exception.CommonErrorEnum;
import hochenchong.duchat.common.common.exception.CustomException;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁
 *
 * @author hochenchong
 * @date 2024/07/25
 */
@Service
public class LockService {
    @Autowired
    private RedissonClient redissonClient;

    public <T> T executeWithLock(String key, int waitTime, TimeUnit unit, LockSupplier<T> supplier) throws Throwable {
        RLock lock = redissonClient.getLock(key);
        boolean lockSuccess = lock.tryLock(waitTime, unit);
        if (!lockSuccess) {
            throw new CustomException(CommonErrorEnum.REQUEST_BUSY);
        }
        try {
            return supplier.get();
        } finally {
            lock.unlock();
        }
    }

    public <T> T executeWithLock(String key, LockSupplier<T> supplier) throws Throwable {
        RLock lock = redissonClient.getLock(key);
        boolean lockSuccess = lock.tryLock(-1, TimeUnit.MILLISECONDS);
        if (!lockSuccess) {
            throw new CustomException(CommonErrorEnum.REQUEST_BUSY);
        }
        try {
            return supplier.get();
        } finally {
            lock.unlock();
        }
    }

    @FunctionalInterface
    public interface LockSupplier<T> {
        /**
         * Gets a result.
         *
         * @return a result
         */
        T get() throws Throwable;
    }
}
