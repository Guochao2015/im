package org.im.server;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.Attribute;
import io.netty.util.ReferenceCountUtil;
import org.im.cache.FirstLevelCache;
import org.im.disruptor.JIDOfflineProducer;
import org.im.handler.ChannelHandler;
import org.im.session.SessionManage;
import org.im.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.xmpp.packet.Packet;

@Component("handler")
@io.netty.channel.ChannelHandler.Sharable
public class IMChannelHandler extends ChannelHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(IMChannelHandler.class);

    @Autowired
    JIDOfflineProducer jidOfflineProducer;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try{
            Packet packet = (Packet)msg;
            log.info(packet.toXML());
            ChannelHandler handler = (ChannelHandler) FirstLevelCache.get("Handler").get(packet.getElement().getName());
            if (!ObjectUtils.isEmpty(handler)){
                handler.process(packet,ctx);
            }
        }finally {
            ReferenceCountUtil.release(msg);//释放
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       log.error(cause.getMessage(),cause);
        ctx.close();
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        super.disconnect(ctx, promise);
        Attribute<SessionManage> attr = ctx.attr(Utils.SESSION_KEY);
        if (!ObjectUtils.isEmpty(attr) && !ObjectUtils.isEmpty(attr.get())){
            //login attr.setIfAbsent(new SessionManage());
            SessionManage sessionManage = attr.get();
            attr.remove();
            //使用 disruptor
            jidOfflineProducer.push(sessionManage.getJid());
        }
    }
}
