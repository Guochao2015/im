package org.im.disruptor;

import com.lmax.disruptor.EventFactory;
import org.springframework.stereotype.Component;

@Component
public class JIDOfflineFactory implements EventFactory<JIDOfflineFactory.JIDOffline> {
    @Override
    public JIDOffline newInstance() {
        return new JIDOffline();
    }

    public class JIDOffline {
        private String jid;

        public String getJid() {
            return jid;
        }

        public void setJid(String jid) {
            this.jid = jid;
        }
    }
}
