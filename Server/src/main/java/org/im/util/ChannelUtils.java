package org.im.util;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.im.session.IoSession;

import java.net.InetSocketAddress;

public class ChannelUtils {

    public static AttributeKey<IoSession> SESSION_KEY = AttributeKey.valueOf("session");

    public static String getIp(Channel channel){
        return ((InetSocketAddress)channel.remoteAddress()).getAddress().toString().substring(1);
    }
}
