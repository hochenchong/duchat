package hochenchong.duchat.common.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 道具类型
 *
 * @author hochenchong
 * @date 2024/07/24
 */
@AllArgsConstructor
@Getter
public enum ItemTypeEnum {
    MODIFY_NAME_CARD(1, "改名卡"),

    BADGE(2, "徽章"),
    ;

    private final Integer type;
    private final String desc;

    private static final Map<Integer, ItemTypeEnum> cache;

    static {
        cache = Arrays.stream(ItemTypeEnum.values()).collect(Collectors.toMap(ItemTypeEnum::getType, Function.identity()));
    }

    public static ItemTypeEnum of(Integer type) {
        return cache.get(type);
    }
}
