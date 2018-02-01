package org.im.util;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.im.cache.FirstLevelCache;
import org.im.session.Session;
import org.im.session.SessionManage;

import java.net.InetSocketAddress;
import java.util.HashMap;

public class Utils {

    public static AttributeKey<SessionManage> SESSION_KEY = AttributeKey.valueOf("session");

    static {
        FirstLevelCache.setMap("SESSION_MANGE", new HashMap<>(100));
    }

    public static String getIp(Channel channel){
        return ((InetSocketAddress)channel.remoteAddress()).getAddress().toString().substring(1);
    }

    public static SessionManage getSessionManage(String jid){
        return (SessionManage) FirstLevelCache.get("SESSION_MANGE".intern()).get(jid);
    }
    public static SessionManage removeSessionManage(String jid){
        return (SessionManage) FirstLevelCache.get("SESSION_MANGE".intern()).remove(jid);
    }
}
