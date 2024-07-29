package hochenchong.duchat.common.user.service;

/**
 * IP 服务接口
 *
 * @author hochenchong
 * @date 2024/07/29
 */
public interface IpService {
    /**
     * 异步更新用户 IP 详情
     * @param uid 用户 id
     */
    void refreshIpDetailAsync(Long uid);
}
