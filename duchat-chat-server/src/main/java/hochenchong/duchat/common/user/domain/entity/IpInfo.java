package hochenchong.duchat.common.user.domain.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

/**
 * 用户 ip 信息
 */
@Data
public class IpInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 注册时的 IP
     */
    private String createIp;
    /**
     * 注册时的 IP 详情
     */
    private IpDetail createIpDetail;
    /**
     * 最新登录的 IP
     */
    private String updateIp;
    /**
     * 最新登录的 IP 详情
     */
    private IpDetail updateIpDetail;

    public void refreshIp(String ip) {
        if (StringUtils.isEmpty(ip)) {
            return;
        }
        updateIp = ip;
        if (createIp == null) {
            createIp = ip;
        }
    }

    /**
     * 需要刷新的 IP
     *
     * @return 需要刷新的 ip
     */
    public String needRefreshIp() {
        boolean notNeedRefresh = Optional.ofNullable(updateIpDetail)
                .map(IpDetail::getIp)
                .filter(ip -> Objects.equals(ip, updateIp))
                .isPresent();
        return notNeedRefresh ? null : updateIp;
    }

    /**
     * 刷新 IP 信息
     *
     * @param ipDetail IP 信息
     */
    public void refreshIpDetail(IpDetail ipDetail) {
        if (Objects.equals(createIp, ipDetail.getIp())) {
            createIpDetail = ipDetail;
        }
        if (Objects.equals(updateIp, ipDetail.getIp())) {
            updateIpDetail = ipDetail;
        }
    }
}