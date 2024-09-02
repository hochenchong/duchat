package hochenchong.duchat.common.chat.service.strategy.msg;

import hochenchong.duchat.common.chat.dao.MessageDao;
import hochenchong.duchat.common.chat.domain.entity.Message;
import hochenchong.duchat.common.chat.domain.entity.msg.MessageExtra;
import hochenchong.duchat.common.chat.domain.entity.msg.TextMsgDTO;
import hochenchong.duchat.common.chat.domain.enums.MsgTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author hochenchong
 * @date 2024/08/02
 */
@Component
public class TextMsgHandler extends AbstractMsgHandler<TextMsgDTO> {
    @Autowired
    private MessageDao messageDao;

    @Override
    MsgTypeEnum getMsgTypeEnum() {
        return MsgTypeEnum.TEXT;
    }

    @Override
    public void checkMsg(Long roodId, Long uid, TextMsgDTO body) {
        // TODO 校验屏蔽字
    }

    @Override
    void saveMsg(Message msg, TextMsgDTO body) {
        // 保存图片相关信息
        MessageExtra extra = Optional.ofNullable(msg.getExtra()).orElse(new MessageExtra());
        extra.setTextMsgDTO(body);

        Message update = new Message();
        update.setId(msg.getId());
        update.setExtra(extra);
        messageDao.updateById(update);
    }

    @Override
    public Object showMsg(Message msg) {
        return msg.getExtra().getTextMsgDTO();
    }
}
