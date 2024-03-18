package hochenchong.duchat.common.websocket.domain.vo.resp;

public class WSBaseResp<T> {
    /**
     * @see hochenchong.duchat.common.websocket.domain.enums.WSRespTypeEnum
     */
    private Integer type;
    private T data;
}
