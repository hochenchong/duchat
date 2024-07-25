package hochenchong.duchat.common.common.utils;

import hochenchong.duchat.common.utils.PasswordUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author hochenchong
 * @date 2024/07/22
 */
public class PasswordUtilTest {

    @Test
    public void testPassword() {
        String salt = PasswordUtil.generateSalt();
        String password = "21232f297a57a5a743894a0e4a801fc3";
        String hashPassword = PasswordUtil.hashPassword(password, salt);
        System.out.println(hashPassword);
        Assertions.assertTrue(PasswordUtil.checkPassword(password, hashPassword));
    }
}