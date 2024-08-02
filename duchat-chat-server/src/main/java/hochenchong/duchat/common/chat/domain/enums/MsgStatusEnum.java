package hochenchong.duchat.common.chat.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 消息状态
 *
 * @author hochenchong
 * @date 2024/08/02
 */
@AllArgsConstructor
@Getter
public enum MsgStatusEnum {
    NORMAL(0, "正常"),
    DELETE(1, "删除"),
    ;

    private final Integer status;
    private final String desc;

    private static Map<Integer, MsgStatusEnum> cache;

    static {
        cache = Arrays.stream(MsgStatusEnum.values()).collect(Collectors.toMap(MsgStatusEnum::getStatus, Function.identity()));
    }

    public static MsgStatusEnum of(Integer type) {
        return cache.get(type);
    }
}
