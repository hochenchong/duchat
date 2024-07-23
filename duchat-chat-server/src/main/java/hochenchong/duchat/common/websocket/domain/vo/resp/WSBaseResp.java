package hochenchong.duchat.common.websocket.domain.vo.resp;

import lombok.Data;

@Data
public class WSBaseResp<T> {
    /**
     * @see hochenchong.duchat.common.websocket.domain.enums.WSRespTypeEnum
     */
    private Integer type;
    private T data;
}
