package org.im.disruptor;


import com.lmax.disruptor.EventHandler;
import org.im.session.SessionManage;
import org.im.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JIDOfflineHandler implements EventHandler<JIDOfflineFactory.JIDOffline> {

    private static Logger LOGGER = LoggerFactory.getLogger(JIDOfflineHandler.class);

    @Override
    public void onEvent(JIDOfflineFactory.JIDOffline jidOffline, long sequence, boolean endOfBatch) throws Exception {
        SessionManage sessionManage = Utils.removeSessionManage(jidOffline.getJid());

        LOGGER.info("当前用户 [{}] 下线了",jidOffline.getJid());

        //通知与之相关的好友  下线了
    }
}
