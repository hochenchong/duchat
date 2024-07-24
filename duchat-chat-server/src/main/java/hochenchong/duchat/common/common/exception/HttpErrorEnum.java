package hochenchong.duchat.common.common.exception;

import hochenchong.duchat.common.common.domain.vo.response.ApiResult;
import hochenchong.duchat.common.utils.JsonUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;

/**
 * @author hochenchong
 * @date 2024/07/24
 */
@AllArgsConstructor
public enum HttpErrorEnum {

    ACCESS_DENIED(401, "登录失败请重新登录"),
    ;

    private int httpCode;
    private String desc;


    public void sendHttpError(HttpServletResponse response) throws IOException {
        response.setStatus(httpCode);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JsonUtils.toStr(ApiResult.fail(httpCode, desc)));
    }
}
