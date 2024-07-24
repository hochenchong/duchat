package hochenchong.duchat.common.common.domain.dto;

import lombok.Data;

/**
 * 请求信息
 *     请求时存放解析用户的信息
 * @author hochenchong
 * @date 2024/07/24
 */
@Data
public class RequestInfo {
    private Long uid;
    private String ip;
}
