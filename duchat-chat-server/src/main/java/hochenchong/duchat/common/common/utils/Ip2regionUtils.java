package hochenchong.duchat.common.common.utils;

import hochenchong.duchat.common.user.domain.entity.IpDetail;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.RandomAccessFile;

/**
 * Ip2region 工具类
 *     并发使用，用整个 xdb 数据缓存创建的查询对象可以安全的用于并发，也就是你可以把这个 searcher 对象做成全局对象去跨线程访问。
 *
 * @author hochenchong
 * @date 2024/07/29
 */
@Slf4j
public class Ip2regionUtils {
    private static final String DB_PATH = "ip/ip2region.xdb";
    private static Searcher searcher;

    static {
        Resource resource = new ClassPathResource(DB_PATH);
        if (resource.exists()) {
            try (RandomAccessFile randomAccessFile = new RandomAccessFile(resource.getFile(), "r")) {
                byte[] cBuff = Searcher.loadContent(randomAccessFile);
                // 使用 cBuff 创建一个完全基于内存的查询对象
                searcher = Searcher.newWithBuffer(cBuff);
            } catch (Exception e) {
                searcher = null;
                log.error("Ip2regionUtils failed to create searcher: {}", e.getMessage());
            }
        } else {
            log.error("Ip2regionUtils IP2Region database file not found at path: {}", DB_PATH);
        }
    }

    /**
     * 根据 IP 获取归属地信息
     *
     * @param ip IP 地址
     * @return 归属地信息
     */
    public static String getRegionByIp(String ip) {
        if (searcher == null) {
            log.error("Ip2regionUtils searcher not initialized. Cannot perform IP lookup.");
            return null;
        }
        try {
            return searcher.search(ip);
        } catch (Exception e) {
            log.error("Ip2regionUtils failed to search IP: {}, error: {}", ip, e.getMessage());
            return null;
        }
    }

    /**
     * 根据 IP 获取归属地信息
     *
     * @param ip IP 地址
     * @return 归属地信息
     */
    public static IpDetail getIpDetailByIp(String ip) {
        String regionByIp = getRegionByIp(ip);
        if (StringUtils.isEmpty(regionByIp)) {
            return null;
        }
        // 中国|0|xx省|xx市|电信
        String[] split = regionByIp.split("\\|");
        if (split.length < 5) {
            return null;
        }
        IpDetail ipDetail = new IpDetail();
        ipDetail.setIp(ip);
        ipDetail.setCountry(split[0]);
        ipDetail.setRegion(split[2]);
        ipDetail.setCity(split[3]);
        ipDetail.setIsp(split[4]);
        return ipDetail;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            String ip = "1.2.3.4";
            String region = getRegionByIp(ip);
            System.out.println(region);
            IpDetail ipDetailByIp = getIpDetailByIp(ip);
            System.out.println(ipDetailByIp);
        }

        // 4、关闭资源 - 该 searcher 对象可以安全用于并发，等整个服务关闭的时候再关闭 searcher
        // searcher.close();
    }
}
