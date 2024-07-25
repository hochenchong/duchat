package hochenchong.duchat.common.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hochenchong
 * @date 2024/07/25
 */
@AllArgsConstructor
@Getter
public enum IdempotentEnum {
    UID(1, "uid"),
    MSG_ID(2, "消息id"),
    ;

    private final int type;
    private final String desc;
}
