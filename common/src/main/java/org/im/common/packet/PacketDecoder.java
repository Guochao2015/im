package org.im.common.packet;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.im.common.enums.XMPPNodeType;
import org.xmpp.packet.IQ;
import org.xmpp.packet.Message;
import org.xmpp.packet.Presence;

import java.util.List;

public class PacketDecoder extends MessageToMessageDecoder<String>{

    public String limiter = null;

    public PacketDecoder(String limiter) {
        this.limiter = limiter;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        if (StringUtils.isNotBlank(msg)){
            Document document = DocumentHelper.parseText(msg);
            Element rootElement = document.getRootElement();
            XMPPNodeType xmppNodeType = XMPPNodeType.valueOf(rootElement.getName());
            switch (xmppNodeType){
                case iq:         out.add(new IQ(rootElement));          break;
                case presence:   out.add(new Presence(rootElement));    break;
                case message:   out.add(new Message(rootElement));      break;
                default: out.add(rootElement);
            }
        }
    }

    public static void main(String[] args) throws DocumentException {
        Document document = DocumentHelper.parseText("<html><head></head></html>");
       System.out.print(document.getRootElement().getName());
    }
}
