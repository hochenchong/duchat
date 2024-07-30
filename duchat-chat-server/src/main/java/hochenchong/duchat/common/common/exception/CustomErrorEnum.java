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
    BADGE_ITEM_NOT_OWN(400_000_004, "还未拥有该徽章"),
    ITEM_TYPE_ERROR(400_000_005, "道具类型错误"),
    NO_PERMISSION(400_000_006, "没有权限"),
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
