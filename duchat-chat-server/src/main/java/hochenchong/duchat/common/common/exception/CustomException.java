package hochenchong.duchat.common.common.exception;

import lombok.Data;

/**
 * 自定义项目异常
 *
 * @author hochenchong
 * @date 2024/07/25
 */
@Data
public class CustomException extends RuntimeException {
    private int code;

    private String message;

    public CustomException(String message) {
        super(message);
        this.message = message;
    }

    public CustomException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public CustomException(ErrorEnum error) {
        super(error.getErrorMsg());
        this.code = error.getErrorCode();
        this.message = error.getErrorMsg();
    }
}
