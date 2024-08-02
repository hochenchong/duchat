package hochenchong.duchat.common.common.constant;

/**
 * @author hochenchong
 * @date 2024/07/22
 */
public class RedisKey {
    private static final String BASE_KEY = "duchat:chat";
    /**
     * 用户 token 的 key
     */
    public static final String USER_TOKEN_STRING = "userToken:uid_%d";
    /**
     * 用户的信息汇总
     */
    public static final String USER_SUMMARY_STRING = "userSummary:uid_%d";
    /**
     * 房间详情
     */
    public static final String ROOM_INFO_STRING = "roomInfo:roomId_%d";

    public static String getKey(String key, Object... o) {
        return BASE_KEY + String.format(key, o);
    }
}
