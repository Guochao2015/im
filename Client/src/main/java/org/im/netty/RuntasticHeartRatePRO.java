package org.im.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.IQ;
import org.xmpp.packet.JID;

import java.net.ConnectException;
import java.util.concurrent.TimeUnit;

/**
 * 发送心跳包
 */
public class RuntasticHeartRatePRO implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuntasticHeartRatePRO.class);

    @Override
    public void run() {
        LOGGER.info("run 心跳包");
        IQ iq = new IQ();
        iq.setFrom(new JID("s@localhost.qq"));
        iq.setTo(new JID("ww@localhost.qq"));
        iq.setChildElement("ping","urn:xmpp:ping");
        try {
            while (true){
                TimeUnit.SECONDS.sleep(5);
                NettyClient.INSTANCE.writeAndFlush(iq);
            }
        } catch (ConnectException e) {
            LOGGER.error(e.getMessage(),e);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(),e);
        }
    }
    public static void main(String[] args){
        IQ iq = new IQ();
        iq.setFrom(new JID("s@localhost.qq"));
        iq.setTo(new JID("ww@localhost.qq"));
        iq.setChildElement("ping","urn:xmpp:ping");
        System.out.println(iq.toXML());
    }
}
