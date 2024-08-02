package hochenchong.duchat.common.chat.service.strategy.msg;

import hochenchong.duchat.common.chat.dao.MessageDao;
import hochenchong.duchat.common.chat.domain.entity.Message;
import hochenchong.duchat.common.chat.domain.enums.MsgTypeEnum;
import hochenchong.duchat.common.chat.domain.vo.req.TextMsgReq;
import hochenchong.duchat.common.chat.domain.vo.resp.TextMsgResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author hochenchong
 * @date 2024/08/02
 */
@Component
public class TextMsgHandler extends AbstractMsgHandler<TextMsgReq> {
    @Autowired
    private MessageDao messageDao;

    @Override
    MsgTypeEnum getMsgTypeEnum() {
        return MsgTypeEnum.TEXT;
    }

    @Override
    void checkMsg(Long roodId, Long uid, TextMsgReq body) {
        // TODO 校验屏蔽字
    }

    @Override
    void saveMsg(Message msg, TextMsgReq body) {
        Message update = new Message();
        update.setId(msg.getId());
        update.setContent(body.getText());
        messageDao.updateById(update);
    }

    @Override
    public Object showMsg(Message msg) {
        TextMsgResp resp = new TextMsgResp();
        resp.setText(msg.getContent());
        return resp;
    }
}
