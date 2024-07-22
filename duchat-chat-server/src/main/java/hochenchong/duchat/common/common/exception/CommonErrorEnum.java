package hochenchong.duchat.common.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用的错误码
 */
@AllArgsConstructor
@Getter
public enum CommonErrorEnum implements ErrorEnum {
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
