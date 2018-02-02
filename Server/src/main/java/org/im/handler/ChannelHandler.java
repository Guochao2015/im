package org.im.handler;

import io.netty.channel.ChannelHandlerContext;
import org.im.exception.PacketException;
import org.im.exception.UnauthorizedException;
import org.xmpp.packet.Packet;

public interface ChannelHandler<T extends Packet>  {

    abstract void process(T packet, ChannelHandlerContext ctx) throws UnauthorizedException, PacketException;
}
