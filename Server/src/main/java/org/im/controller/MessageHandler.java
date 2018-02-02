package org.im.controller;

import io.netty.channel.ChannelHandlerContext;
import org.im.annotation.Node;
import org.im.exception.PacketException;
import org.im.exception.UnauthorizedException;
import org.im.handler.ChannelHandler;
import org.springframework.stereotype.Component;
import org.xmpp.packet.Message;

@Component
@Node(element = "message")
public class MessageHandler implements ChannelHandler<Message> {
    @Override
    public void process(Message packet, ChannelHandlerContext ctx) throws UnauthorizedException, PacketException {

    }
}
