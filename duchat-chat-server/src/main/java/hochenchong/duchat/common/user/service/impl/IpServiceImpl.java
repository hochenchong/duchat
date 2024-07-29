package hochenchong.duchat.common.user.service.impl;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.thread.NamedThreadFactory;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import hochenchong.duchat.common.common.utils.Ip2regionUtils;
import hochenchong.duchat.common.user.dao.UserDao;
import hochenchong.duchat.common.user.domain.dto.IpResult;
import hochenchong.duchat.common.user.domain.entity.IpDetail;
import hochenchong.duchat.common.user.domain.entity.IpInfo;
import hochenchong.duchat.common.user.domain.entity.User;
import hochenchong.duchat.common.user.service.IpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Ip 详情服务
 *
 * @author hochenchong
 * @date 2024/07/29
 */
@Service
@Slf4j
public class IpServiceImpl implements IpService, DisposableBean {
    private static final ExecutorService EXECUTOR = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(500),
            new NamedThreadFactory("refresh-ipDetail", null, false));

    @Autowired
    private UserDao userDao;

    @Override
    public void refreshIpDetailAsync(Long uid) {
        EXECUTOR.execute(() -> {
            User user = userDao.getById(uid);
            IpInfo ipInfo = user.getIpInfo();
            if (Objects.isNull(ipInfo)) {
                return;
            }
            String ip = ipInfo.needRefreshIp();
            if (StrUtil.isBlank(ip)) {
                return;
            }
            IpDetail ipDetail = tryGetIpDetailOrNullThreeTimes(ip);
            if (Objects.nonNull(ipDetail)) {
                ipInfo.refreshIpDetail(ipDetail);
                User update = new User();
                update.setId(uid);
                update.setIpInfo(ipInfo);
                userDao.updateById(update);
            } else {
                log.error("get ip detail fail ip:{},uid:{}", ip, uid);
            }

        });
    }

    private static IpDetail tryGetIpDetailOrNullThreeTimes(String ip) {
        // 优先从本地查询
        IpDetail ipDetail = Ip2regionUtils.getIpDetailByIp(ip);
        if (ipDetail != null) {
            return ipDetail;
        }

        // 查询不到则调用淘宝接口查询
        for (int i = 0; i < 3; i++) {
            ipDetail = getIpDetailOrNull(ip);
            if (Objects.nonNull(ipDetail)) {
                return ipDetail;
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {
            }
        }
        return null;
    }

    public static IpDetail getIpDetailOrNull(String ip) {
        String body = HttpUtil.get("https://ip.taobao.com/outGetIpInfo?ip=" + ip + "&accessKey=alibaba-inc");
        try {
            IpResult<IpDetail> result = JSONUtil.toBean(body, new TypeReference<>() {}, false);
            if (result.isSuccess()) {
                return result.getData();
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * 优雅停机
     *
     * @throws Exception 异常
     */
    @Override
    public void destroy() throws Exception {
        EXECUTOR.shutdown();
        // 等待一段事件用于处理未执行完的任务，时间到没处理完就算了
        if (!EXECUTOR.awaitTermination(30, TimeUnit.SECONDS)) {
            if (log.isErrorEnabled()) {
                log.error("Timed out while waiting for executor [{}] to terminate", EXECUTOR);
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            EXECUTOR.execute(() -> {
                long start = System.currentTimeMillis();
                IpDetail ipDetail = tryGetIpDetailOrNullThreeTimes("1.2.3.4");
                if (Objects.nonNull(ipDetail)) {
                    System.out.printf("第%d次成功,目前耗时：%dms%n", finalI, (System.currentTimeMillis() - start));
                }
            });
        }
    }
}
