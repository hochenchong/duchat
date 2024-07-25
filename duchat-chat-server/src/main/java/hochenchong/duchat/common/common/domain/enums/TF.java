package hochenchong.duchat.common.common.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hochenchong
 * @date 2024/07/24
 */
@AllArgsConstructor
@Getter
public enum TF {
    YES(1, "是"),
    NO(0, "否"),
    ;

    private final int status;

    private final String desc;
}
