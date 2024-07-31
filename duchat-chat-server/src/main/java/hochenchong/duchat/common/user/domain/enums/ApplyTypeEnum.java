package hochenchong.duchat.common.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplyTypeEnum {

    ADD_FRIEND(1, "加好友"),
    ADD_GROUP(2, "加群"),
    ;


    private final Integer code;

    private final String desc;
}
