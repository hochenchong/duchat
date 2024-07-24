package hochenchong.duchat.common.common.utils;

import hochenchong.duchat.common.common.domain.dto.RequestInfo;

/**
 * 请求上下文
 *
 * @author hochenchong
 * @date 2024/07/24
 */
public class RequestHolder {
    private static final ThreadLocal<RequestInfo> threadLocal = new ThreadLocal<>();

    public static void set(RequestInfo requestInfo) {
        threadLocal.set(requestInfo);
    }

    public static RequestInfo get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}
