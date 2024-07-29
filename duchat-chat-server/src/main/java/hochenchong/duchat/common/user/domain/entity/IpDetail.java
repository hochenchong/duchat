package hochenchong.duchat.common.user.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户 ip 信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IpDetail implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * ip
     */
    private String ip;
    /**
     * 运营商
     */
    private String isp;
    /**
     * 国家
     */
    private String country;
    /**
     * 省份
     */
    private String region;
    /**
     * 城市
     */
    private String city;
}