package hochenchong.duchat.common.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 项目里自定义的错误码
 */
@AllArgsConstructor
@Getter
public enum CustomErrorEnum implements ErrorEnum {
    PARAM_INVALID(400_000_001, "参数校验失败"),
    NAME_ALREADY_EXISTS(400_000_002, "名字已存在"),
    MODIFY_NAME_ITEM_NOT_ENOUGH(400_000_003, "改名卡道具不足"),
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
