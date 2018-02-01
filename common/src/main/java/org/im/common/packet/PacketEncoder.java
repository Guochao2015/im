package org.im.common.packet;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.xmpp.packet.Packet;

import java.util.List;

public class PacketEncoder extends MessageToMessageEncoder<org.xmpp.packet.Packet> {

    public String limiter = null;

    public PacketEncoder(String limiter) {
        this.limiter = limiter;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, List<Object> out) throws Exception {
        if (msg != null){
            String resp = msg.toXML();
            out.add(resp.concat(limiter));
        }
    }
}
