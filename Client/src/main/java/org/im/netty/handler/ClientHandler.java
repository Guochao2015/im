package org.im.netty.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.im.desktop.controller.Controller;
import org.im.netty.NettyClient;
import org.im.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.IQ;
import org.xmpp.packet.JID;
import org.xmpp.packet.Packet;

public class ClientHandler  extends ChannelHandlerAdapter{

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientHandler.class);

    private static final IQ iq;
    static{
        iq = new IQ();
        iq.setFrom(new JID("s@localhost.qq"));
        iq.setTo(new JID("ww@localhost.qq"));
        iq.setChildElement("ping","urn:xmpp:ping");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        NettyClient.INSTANCE.setConnection(false);
        NettyClient.INSTANCE.doConnection();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println(msg);

        Packet packet = (Packet) msg;

        String id = packet.getID();
        Controller call = Util.getCallController(id);
        if (call != null){
            call.packetHandle(packet);
        }else{
            LOGGER.warn("没有找到可用的Controller 丢弃 [{}]",packet.toXML());
        }

//        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    /**
     * 连接断开
     * @param ctx
     * @param promise
     * @throws Exception
     */
    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        super.disconnect(ctx, promise);
        NettyClient.INSTANCE.setConnection(false);
        NettyClient.INSTANCE.doConnection();
    }
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.WRITER_IDLE){
                NettyClient.INSTANCE.writeAndFlush(iq, null); //心跳
            }
        }
    }
}
