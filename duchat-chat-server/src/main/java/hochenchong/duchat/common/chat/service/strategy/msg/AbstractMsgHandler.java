package hochenchong.duchat.common.chat.service.strategy.msg;

import cn.hutool.core.bean.BeanUtil;
import hochenchong.duchat.common.chat.dao.MessageDao;
import hochenchong.duchat.common.chat.domain.entity.Message;
import hochenchong.duchat.common.chat.domain.enums.MsgTypeEnum;
import hochenchong.duchat.common.chat.domain.vo.req.ChatMsgReq;
import hochenchong.duchat.common.chat.service.adapter.MessageAdapter;
import hochenchong.duchat.common.common.utils.AssertUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hochenchong
 * @date 2024/08/02
 */
public abstract class AbstractMsgHandler<T> {
    private Class<T> tClass;
    @Autowired
    private MessageDao messageDao;

    @PostConstruct
    private void init() {
        // 使用 Spring 框架的工具获取
        ResolvableType resolvableType = ResolvableType.forClass(getClass());
        this.tClass = (Class<T>) resolvableType.getSuperType().getGeneric(0).resolve();
        // 子类自动注册到工厂之中
        MsgHandlerFactory.register(getMsgTypeEnum().getType(), this);
    }

    abstract MsgTypeEnum getMsgTypeEnum();

    /**
     * 开放给子类的校验逻辑
     *
     * @param roodId 房间 id
     * @param uid 用户 id
     * @param msgContent 消息体
     */
    abstract void checkMsg(Long roodId, Long uid, T msgContent);

    /**
     * 子类扩展保存
     *
     * @param msg 保存的消息
     * @param msgContent 请求的消息
     */
    abstract void saveMsg(Message msg, T msgContent);

    /**
     * 展示消息
     *
     * @param msg 消息
     * @return 展示的方式
     */
    public abstract Object showMsg(Message msg);

    @Transactional
    public Long checkAndSaveMsg(Long uid, ChatMsgReq msgReq) {
        // 统一校验 msgContent 的数据
        T msgContent = this.toBean(msgReq.getMsgContent());
        AssertUtils.checkValidateThrows(msgContent);
        // 子类扩展校验
        checkMsg(msgReq.getRoomId(), uid, msgContent);
        Message insert = MessageAdapter.buildMsgSave(msgReq, uid);
        // 统一保存
        messageDao.save(insert);
        // 子类扩展保存
        saveMsg(insert, msgContent);
        return insert.getId();
    }

    private T toBean(Object msgContent) {
        if (tClass.isAssignableFrom(msgContent.getClass())) {
            return (T) msgContent;
        }
        return BeanUtil.toBean(msgContent, tClass);
    }
}
