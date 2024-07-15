package hochenchong.duchat.common;

import hochenchong.duchat.common.common.utils.JwtUtils;
import hochenchong.duchat.common.user.dao.UserDao;
import hochenchong.duchat.common.user.domain.entity.User;
import hochenchong.duchat.common.utils.RedisUtils;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DaoTest {
    @Resource
    private UserDao userDao;


    @Test
    public void test() {
        User byId = userDao.getById(1);
        System.out.println(byId);

        User insertUser = new User();
        insertUser.setName("111");
        insertUser.setOpenId("123456");
        userDao.save(insertUser);
    }

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    public void testJwt() {
        System.out.println(jwtUtils.createToken(1L));
        System.out.println(jwtUtils.createToken(1L));
        System.out.println(jwtUtils.createToken(1L));
        System.out.println(jwtUtils.createToken(1L));
    }

    @Test
    public void testRedis() {
        RedisUtils.set("测试", "测试一下");
        String str = RedisUtils.getStr("测试");
        System.out.println(str);
    }
}
