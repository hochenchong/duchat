package hochenchong.duchat.common.common.utils;

import hochenchong.duchat.common.user.domain.entity.IpDetail;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.RandomAccessFile;

/**
 * Ip2region 工具
 *     并发使用，用整个 xdb 数据缓存创建的查询对象可以安全的用于并发，也就是你可以把这个 searcher 对象做成全局对象去跨线程访问。
 *
 * @author hochenchong
 * @date 2024/07/29
 */
@Slf4j
public class Ip2regionUtils {
    private static Searcher searcher;

    static {
        String dbPath = "ip/ip2region.xdb";
        byte[] cBuff = new byte[0];
        Resource resource = new ClassPathResource(dbPath);
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(resource.getFile(), "r")) {
            cBuff = Searcher.loadContent(randomAccessFile);
        } catch (Exception e) {
            log.error("Ip2regionUtils failed to load content from {}", dbPath, e);
        }
        // 2、使用上述的 cBuff 创建一个完全基于内存的查询对象。
        if (cBuff.length != 0) {
            try {
                searcher = Searcher.newWithBuffer(cBuff);
            } catch (Exception e) {
                log.error("Ip2regionUtils failed to create content cached searcher: ", e);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            String ip = "1.2.3.4";
            String region = searcher.search(ip);
            System.out.println(region);
            IpDetail ipDetailByIp = getIpDetailByIp(ip);
            System.out.println(ipDetailByIp);
        }
        // 4、关闭资源 - 该 searcher 对象可以安全用于并发，等整个服务关闭的时候再关闭 searcher
        // searcher.close();
    }

    public static String getRegionByIp(String ip) throws Exception {
        return searcher.search(ip);
    }

    public static IpDetail getIpDetailByIp(String ip) throws Exception {
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
}
