package hochenchong.duchat.common.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 角色
 *
 * @author hochenchong
 * @date 2024/07/30
 */
@AllArgsConstructor
@Getter
public enum RoleEnum {
    ADMIN(1, "超级管理员"),
    CHAT_MANAGER(2, "群聊管理员"),
    ;

    private final Integer roleId;
    private final String desc;

    private static final Map<Integer, RoleEnum> cache;

    static {
        cache = Arrays.stream(RoleEnum.values()).collect(Collectors.toMap(RoleEnum::getRoleId, Function.identity()));
    }

    public static RoleEnum of(Integer roleId) {
        return cache.get(roleId);
    }
}
