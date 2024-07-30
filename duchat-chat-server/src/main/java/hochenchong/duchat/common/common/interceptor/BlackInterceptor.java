package hochenchong.duchat.common.common.interceptor;

import hochenchong.duchat.common.common.domain.dto.RequestInfo;
import hochenchong.duchat.common.common.exception.HttpErrorEnum;
import hochenchong.duchat.common.common.utils.RequestHolder;
import hochenchong.duchat.common.user.domain.enums.BlackTypeEnum;
import hochenchong.duchat.common.user.service.cache.UserCache;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 黑名单 拦截器
 *
 * @author hochenchong
 * @date 2024/07/24
 */
@Component
public class BlackInterceptor implements HandlerInterceptor {
    @Autowired
    private UserCache userCache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取黑名单
        Map<Integer, Set<String>> blackMap = userCache.getBlackMap();
        RequestInfo requestInfo = RequestHolder.get();
        if (inBlackList(requestInfo.getUid(), blackMap.get(BlackTypeEnum.UID.getType()))) {
            HttpErrorEnum.ACCESS_DENIED.sendHttpError(response);
            return false;
        }
        if (inBlackList(requestInfo.getIp(), blackMap.get(BlackTypeEnum.IP.getType()))) {
            HttpErrorEnum.ACCESS_DENIED.sendHttpError(response);
            return false;
        }
        return true;
    }

    private boolean inBlackList(Object target, Set<String> blackMap) {
        if (Objects.isNull(target) || CollectionUtils.isEmpty(blackMap)) {
            return false;
        }
        return blackMap.contains(target.toString());
    }
}
