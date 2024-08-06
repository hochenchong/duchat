package hochenchong.duchat.common.chat.service.strategy.msg;

import hochenchong.duchat.common.chat.dao.MessageDao;
import hochenchong.duchat.common.chat.domain.entity.Message;
import hochenchong.duchat.common.chat.domain.entity.msg.FileMsgDTO;
import hochenchong.duchat.common.chat.domain.entity.msg.MessageExtra;
import hochenchong.duchat.common.chat.domain.enums.MsgTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * 文件消息处理
 *
 * @author hochenchong
 * @date 2024/08/06
 */
public class FileMsgHandler extends AbstractMsgHandler<FileMsgDTO> {
    @Autowired
    private MessageDao messageDao;

    @Override
    MsgTypeEnum getMsgTypeEnum() {
        return MsgTypeEnum.FILE;
    }

    @Override
    void saveMsg(Message msg, FileMsgDTO msgContent) {
        // 保存图片相关信息
        MessageExtra extra = Optional.ofNullable(msg.getExtra()).orElse(new MessageExtra());
        extra.setFileMsg(msgContent);

        Message update = new Message();
        update.setId(msg.getId());
        update.setExtra(extra);
        messageDao.updateById(update);
    }

    @Override
    public Object showMsg(Message msg) {
        return msg.getExtra().getFileMsg();
    }
}
