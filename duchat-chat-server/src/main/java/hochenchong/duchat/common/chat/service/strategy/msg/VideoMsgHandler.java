package hochenchong.duchat.common.chat.service.strategy.msg;

import hochenchong.duchat.common.chat.dao.MessageDao;
import hochenchong.duchat.common.chat.domain.entity.Message;
import hochenchong.duchat.common.chat.domain.entity.msg.MessageExtra;
import hochenchong.duchat.common.chat.domain.entity.msg.VideoMsgDTO;
import hochenchong.duchat.common.chat.domain.enums.MsgTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * 视频消息处理
 *
 * @author hochenchong
 * @date 2024/08/06
 */
public class VideoMsgHandler extends AbstractMsgHandler<VideoMsgDTO> {
    @Autowired
    private MessageDao messageDao;

    @Override
    MsgTypeEnum getMsgTypeEnum() {
        return MsgTypeEnum.VIDEO;
    }

    @Override
    void saveMsg(Message msg, VideoMsgDTO msgContent) {
        // 保存图片相关信息
        MessageExtra extra = Optional.ofNullable(msg.getExtra()).orElse(new MessageExtra());
        extra.setVideoMsgDTO(msgContent);

        Message update = new Message();
        update.setId(msg.getId());
        update.setExtra(extra);
        messageDao.updateById(update);
    }

    @Override
    public Object showMsg(Message msg) {
        return msg.getExtra().getVideoMsgDTO();
    }
}
