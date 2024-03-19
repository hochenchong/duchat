package hochenchong.duchat.common;

import hochenchong.duchat.common.user.dao.UserDao;
import hochenchong.duchat.common.user.domain.entity.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
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
}
