package hochenchong.duchat.common.common.thread;

import java.util.concurrent.ThreadFactory;

/**
 * 自定义线程工厂
 *     装饰者模式，强化线程的功能，在抛出异常时，打印日志
 *
 * @author hochenchong
 * @date 2024/07/22
 */
public class MyThreadFactory implements ThreadFactory {
    private static final MyUncaughtExceptionHandler MY_UNCAUGHT_EXCEPTION_HANDLER = new MyUncaughtExceptionHandler();

    private final ThreadFactory threadFactory;

    public MyThreadFactory(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = threadFactory.newThread(r);
        // 额外装饰需要的逻辑
        thread.setUncaughtExceptionHandler(MY_UNCAUGHT_EXCEPTION_HANDLER);
        return thread;
    }
}
