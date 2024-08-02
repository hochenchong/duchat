package hochenchong.duchat.common.chat.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 消息类型
 *
 * @author hochenchong
 * @date 2024/08/02
 */
@AllArgsConstructor
@Getter
public enum MsgTypeEnum {
    TEXT(1, "文本"),
    IMG(2, "图片"),
    SOUND(3, "语音"),
    VIDEO(4, "视频"),
    FILE(5, "文件"),
    EMOJI(6, "表情"),
    SYSTEM(99, "系统消息"),
    ;

    private final Integer type;
    private final String desc;

    private static Map<Integer, MsgTypeEnum> cache;

    static {
        cache = Arrays.stream(MsgTypeEnum.values()).collect(Collectors.toMap(MsgTypeEnum::getType, Function.identity()));
    }

    public static MsgTypeEnum of(Integer type) {
        return cache.get(type);
    }
}
