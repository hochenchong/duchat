package hochenchong.duchat.common.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用的错误码
 */
@AllArgsConstructor
@Getter
public enum CommonErrorEnum implements ErrorEnum {
    SUCCESS(0, "成功"),
    SYSTEM_ERROR(-1, "系统错误"),
    REQUEST_BUSY(-2, "请求太频繁了，请稍后再试！"),
    FREQUENCY_LIMIT(-3, "请求太频繁了，请稍后再试！"),
    ;

    private final Integer code;
    private final String msg;

    @Override
    public Integer getErrorCode() {
        return code;
    }

    @Override
    public String getErrorMsg() {
        return msg;
    }
}
