package hochenchong.duchat.common.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 道具
 *
 * @author hochenchong
 * @date 2024/07/24
 */
@AllArgsConstructor
@Getter
public enum ItemEnum {
    MODIFY_NAME_CARD(1, ItemTypeEnum.MODIFY_NAME_CARD, "改名卡"),
    LIKE_BADGE(2, ItemTypeEnum.BADGE, "点赞达人"),
    REG_TOP100_BADGE(3, ItemTypeEnum.BADGE, "前 100 注册用户专属徽章"),
    ;

    private final Integer id;
    private final ItemTypeEnum typeEnum;
    private final String desc;

    private static final Map<Integer, ItemEnum> cache;

    static {
        cache = Arrays.stream(ItemEnum.values()).collect(Collectors.toMap(ItemEnum::getId, Function.identity()));
    }

    public static ItemEnum of(Integer type) {
        return cache.get(type);
    }
}