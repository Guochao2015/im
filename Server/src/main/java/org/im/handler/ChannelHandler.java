package org.im.handler;

import org.im.exception.PacketException;
import org.im.exception.UnauthorizedException;
import org.xmpp.packet.Packet;

public interface ChannelHandler<T extends Packet>  {

    abstract void process(T packet) throws UnauthorizedException, PacketException;
}
