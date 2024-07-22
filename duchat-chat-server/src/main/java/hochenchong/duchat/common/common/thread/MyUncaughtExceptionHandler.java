package hochenchong.duchat.common.common.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义的异常处理器
 *     用于线程抛异常时，打印到日志里（避免打印到控制台看不到）
 *
 * @author hochenchong
 * @date 2024/07/22
 */
@Slf4j
public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("Exception in thread", e);
    }
}
