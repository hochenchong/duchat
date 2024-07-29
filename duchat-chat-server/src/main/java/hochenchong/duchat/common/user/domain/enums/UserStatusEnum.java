package hochenchong.duchat.common.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hochenchong
 * @date 2024/07/26
 */
@AllArgsConstructor
@Getter
public enum UserStatusEnum {
    ONLINE(1, "在线"),
    OFFLINE(2, "离线"),
    ;

    private final int status;
    private final String desc;
}
