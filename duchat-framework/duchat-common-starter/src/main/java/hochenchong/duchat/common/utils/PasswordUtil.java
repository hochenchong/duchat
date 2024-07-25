package hochenchong.duchat.common.utils;

public class PasswordUtil {

    /**
     * 获取盐值
     *
     * @return 盐
     */
    public static String generateSalt() {
        return BCrypt.gensalt();
    }

    /**
     * 返回 hash 过的密码
     *
     * @param plainPassword 用户密码
     * @param salt 盐值
     * @return hash 后的密码
     */
    public static String hashPassword(String plainPassword, String salt) {
        return BCrypt.hashpw(plainPassword, salt);
    }

    /**
     * 校验密码
     *
     * @param plainPassword 原始密码
     * @param hashedPassword hash 后的密码
     * @return 是否正确
     */
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}