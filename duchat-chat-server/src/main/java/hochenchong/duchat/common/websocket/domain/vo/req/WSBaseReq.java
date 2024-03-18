package hochenchong.duchat.common.websocket.domain.vo.req;

import lombok.Data;

/**
 * 前端请求
 */
@Data
public class WSBaseReq {
    /**
     * 请求类型
     */
    private Integer type;
    /**
     * 封装的数据
     */
    private String data;
}
