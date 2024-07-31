package hochenchong.duchat.common.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplyStatusEnum {

    PENDING(1, "待审批"),

    AGREE(2, "同意"),
    ;

    private final Integer code;

    private final String desc;
}