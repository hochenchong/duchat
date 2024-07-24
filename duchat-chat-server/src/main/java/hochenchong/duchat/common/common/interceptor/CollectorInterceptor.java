package hochenchong.duchat.common.common.interceptor;

import cn.hutool.extra.servlet.JakartaServletUtil;
import hochenchong.duchat.common.common.constant.CommonConstants;
import hochenchong.duchat.common.common.domain.dto.RequestInfo;
import hochenchong.duchat.common.common.utils.RequestHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

/**
 * 信息收集的拦截器
 *
 * @author hochenchong
 * @date 2024/07/24
 */
@Component
public class CollectorInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long uid = Optional.ofNullable(request.getAttribute(CommonConstants.UID)).map(Object::toString)
                .map(Long::parseLong).orElse(null);
        String ip = JakartaServletUtil.getClientIP(request);
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setUid(uid);
        requestInfo.setIp(ip);
        RequestHolder.set(requestInfo);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 使用后，则移除掉
        RequestHolder.remove();
    }
}