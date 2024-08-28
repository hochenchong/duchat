package hochenchong.duchat.common.chat.controller;

import hochenchong.duchat.common.chat.domain.vo.req.ChatMsgReq;
import hochenchong.duchat.common.chat.domain.vo.resp.ChatMsgResp;
import hochenchong.duchat.common.chat.service.ChatService;
import hochenchong.duchat.common.common.annotation.FrequencyControl;
import hochenchong.duchat.common.common.annotation.FrequencyControlContainer;
import hochenchong.duchat.common.common.domain.vo.response.ApiResult;
import hochenchong.duchat.common.common.utils.RequestHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 聊天模块
 *
 * @author hochenchong
 * @date 2024/08/02
 */
@RestController
@RequestMapping("/capi/chat")
@Tag(name = "聊天")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @PostMapping("/msg")
    @Operation(summary = "发送消息")
    @FrequencyControlContainer({
            @FrequencyControl(time = 5, count = 2, target = FrequencyControl.Target.UID),
            @FrequencyControl(time = 30, count = 5, target = FrequencyControl.Target.UID),
            @FrequencyControl(time = 60, count = 10, target = FrequencyControl.Target.UID)
    })
    public ApiResult<ChatMsgResp> sendMsg(@Valid @RequestBody ChatMsgReq req) {
        Long msgId = chatService.sendMsg(RequestHolder.get().getUid(), req);
        // 返回完整消息格式，方便前端展示
        return ApiResult.success(chatService.getMsgResp(msgId));
    }
}
