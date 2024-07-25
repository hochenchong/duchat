package hochenchong.duchat.common;

import hochenchong.duchat.common.common.config.ThreadPoolConfig;
import hochenchong.duchat.common.common.utils.JwtUtils;
import hochenchong.duchat.common.user.dao.UserDao;
import hochenchong.duchat.common.user.domain.entity.User;
import hochenchong.duchat.common.user.domain.enums.IdempotentEnum;
import hochenchong.duchat.common.user.service.LoginService;
import hochenchong.duchat.common.user.service.UserBackpackService;
import hochenchong.duchat.common.utils.RedisUtils;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootTest
public class DaoTest {
    @Resource
    private UserDao userDao;

    @Autowired
    private LoginService loginService;

    @Test
    public void testGetToken() {
        System.out.println(loginService.login(1L));
    }


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

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void testRedisLock() {
        RLock lock = redissonClient.getLock("123");
        lock.lock();
        System.out.println();
        lock.unlock();
    }

    @Autowired
    @Qualifier(ThreadPoolConfig.DUCHAT_EXECUTOR)
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Test
    public void testThread() throws InterruptedException {
        threadPoolTaskExecutor.execute(() -> {
            if (1 == 1) {
                throw new RuntimeException("123");
            }
        });
        Thread.sleep(200);
    }

    @Autowired
    private UserBackpackService userBackpackService;

    /**
     * 测试发道具
     */
    @Test
    public void testAcquireItem() {
        userBackpackService.acquireItem(1L, 2, IdempotentEnum.UID, "1");
    }
}
