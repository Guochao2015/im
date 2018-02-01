package org.im.controller;

import org.im.annotation.Node;
import org.im.exception.PacketException;
import org.im.exception.UnauthorizedException;
import org.im.handler.ChannelHandler;
import org.springframework.stereotype.Component;
import org.xmpp.packet.Presence;

@Component
@Node(element = "presence")
public class PresenceHandler implements ChannelHandler<Presence> {
    @Override
    public void process(Presence packet) throws UnauthorizedException, PacketException {
        System.out.println(packet.toXML());
    }
}
