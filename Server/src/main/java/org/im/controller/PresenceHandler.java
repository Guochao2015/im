package org.im.controller;

import io.netty.channel.ChannelHandlerContext;
import org.im.annotation.Node;
import org.im.exception.PacketException;
import org.im.exception.UnauthorizedException;
import org.im.handler.ChannelHandler;
import org.im.session.SessionManage;
import org.im.util.Utils;
import org.springframework.stereotype.Component;
import org.xmpp.packet.Presence;

@Component
@Node(element = "presence")
public class PresenceHandler implements ChannelHandler<Presence> {
    @Override
    public void process(Presence packet, ChannelHandlerContext ctx) throws UnauthorizedException, PacketException {

        Presence.Type type = packet.getType();
        switch (type){
            case subscribe:
                SessionManage sessionManage = new SessionManage();
                sessionManage.setChannel(ctx);
                sessionManage.setHostAddress(Utils.getIp(ctx.channel()));
                sessionManage.setJid(packet.getTo().toBareJID());
                sessionManage.setValidate(true);
                sessionManage.setStatus(1);
                ctx.writeAndFlush(packet);
                break;
            case unsubscribe:break;
            case subscribed:break;
            case unsubscribed:break;
            case unavailable: break;
            case probe:break;
            case error:break;
        }
    }

}
