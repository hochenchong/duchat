package hochenchong.duchat.common.websocket.domain.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 群成员列表的成员信息
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMemberResp {
    @Schema(description = "uid")
    private Long uid;
    @Schema(description = "在线状态 1在线 2离线")
    private Integer activeStatus;
    @Schema(description = "最后一次上下线时间")
    private Date lastOptTime;
}
