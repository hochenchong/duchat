package hochenchong.duchat.common.common.interceptor;

import hochenchong.duchat.common.common.constant.CommonConstants;
import hochenchong.duchat.common.common.exception.HttpErrorEnum;
import hochenchong.duchat.common.user.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;
import java.util.Optional;

/**
 * Token 拦截器
 *     解析携带的 token 信息，获取用户
 * @author hochenchong
 * @date 2024/07/24
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String AUTHORIZATION_SCHEMA = "Bearer ";

    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求获取 token
        String token = getToken(request);
        // 判断 token 是否有效
        Long validUid = loginService.getValidUid(token);
        // 如果为空，则代表无效类
        if (Objects.isNull(validUid)) {
            // 判断是否是一定要登录的网站
            boolean isPublicURI = isPublicURI(request.getRequestURI());
            if (isPublicURI) {
                return true;
            }
            // 为空则返回 401
            HttpErrorEnum.ACCESS_DENIED.sendHttpError(response);
            return false;
        }
        request.setAttribute(CommonConstants.UID, validUid);
        return true;
    }

    private boolean isPublicURI(String requestURI) {
        String[] split = requestURI.split("/");
        return split.length > 3 && "public".equals(split[3]);
    }

    /**
     * 从请求头信息中获取 token
     *
     * @param request 请求
     * @return token 信息
     */
    private String getToken(HttpServletRequest request) {
        String header = request.getHeader(HEADER_AUTHORIZATION);
        return Optional.ofNullable(header).filter(h -> h.startsWith(AUTHORIZATION_SCHEMA))
                .map(h -> h.replaceFirst(AUTHORIZATION_SCHEMA, ""))
                .orElse(null);
    }
}
