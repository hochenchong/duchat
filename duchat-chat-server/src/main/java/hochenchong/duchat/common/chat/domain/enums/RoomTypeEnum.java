package hochenchong.duchat.common.chat.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 房间类型
 *
 * @author hochenchong
 * @date 2024/08/02
 */
@AllArgsConstructor
@Getter
public enum RoomTypeEnum {
    FRIEND(1, "单聊"),
    GROUP(2, "群聊"),
    ;

    private final Integer type;
    private final String desc;

    private static Map<Integer, RoomTypeEnum> cache;

    static {
        cache = Arrays.stream(RoomTypeEnum.values()).collect(Collectors.toMap(RoomTypeEnum::getType, Function.identity()));
    }

    public static RoomTypeEnum of(Integer type) {
        return cache.get(type);
    }
}
