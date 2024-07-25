package hochenchong.duchat.common.common.exception;

import hochenchong.duchat.common.common.domain.vo.response.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常捕获处理
 *
 * @author hochenchong
 * @date 2024/07/25
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常处理
     *
     * @param e 业务异常
     * @return 返回统一回包
     */
    @ExceptionHandler(value = CustomException.class)
    public ApiResult<?> customException(CustomException e) {
        return ApiResult.fail(e.getCode(), e.getMessage());
    }

    /**
     * 保底，确保所有异常都被捕获
     *
     * @param e 异常
     * @return 返回统一回包
     */
    @ExceptionHandler(value = Exception.class)
    public ApiResult<?> exception(Exception e) {
        log.error("System exception! The reason is: {}", e.getMessage(), e);
        return ApiResult.fail(CommonErrorEnum.SYSTEM_ERROR);
    }
}
