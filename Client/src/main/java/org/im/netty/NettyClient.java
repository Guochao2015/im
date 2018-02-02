package org.im.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.commons.lang3.StringUtils;
import org.im.common.CommonUtils;
import org.im.common.packet.PacketEncoder;
import org.im.common.packet.PacketDecoder;
import org.im.desktop.controller.Controller;
import org.im.netty.handler.ClientHandler;
import org.im.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.Packet;
import org.xmpp.packet.Presence;

import java.net.ConnectException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

public enum NettyClient {

    INSTANCE,
    ;
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClient.class);

    private Bootstrap b = null;

    private ChannelFuture channelFuture;

    private volatile boolean isConnection;

    public String limiter = CommonUtils.LIMITER;

    public String host = Util.getPropVal("server.host","127.0.0.1");
    public int   port  = Util.getPropVal("server.port",5222);

    EventLoopGroup workgroup = null;
    NettyClient(){
        workgroup = new NioEventLoopGroup();
        b = new Bootstrap();
        b.group(workgroup)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        sc.pipeline()
                                .addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer(CommonUtils.LIMITER.getBytes("UTF-8"))))//为了解决粘包
                                .addLast(new StringEncoder(Charset.forName("UTF-8")))
                                .addLast(new StringDecoder(Charset.forName("UTF-8")))
                                .addLast(new PacketDecoder(limiter))
                                .addLast(new PacketEncoder(limiter))
                                .addLast(new ClientHandler());
                    }
                });
    }


    /**
     * 连接服务器
     * @throws InterruptedException
     */
    public void doConnection() throws InterruptedException {

        Util.getExecutorService().execute(()->{
            try {
                channelFuture = b.connect(host, port).sync();
                channelFuture.addListener((future) -> { //监听是否连接上服务器
                    if(future.isSuccess()){
                        setConnection(true);
                        Util.getExecutorService().execute(new RuntasticHeartRatePRO());
                    }else {
                        channelFuture.channel().eventLoop().schedule(()->{ //断开重连
                            LOGGER.info("Disconnect");
                            try {
                                doConnection();
                            } catch (InterruptedException e) {
                                LOGGER.error(e.getMessage(),e);
                            }
                        },3, TimeUnit.SECONDS);
                    }
                });

                channelFuture.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage(),e);
            }
        });
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                NettyClient.INSTANCE.stop();
            }
        });
    }

    /**
     * 断开连接
     */
    public void stop(){
        try {
            channelFuture.channel().close();
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
        }
        workgroup.shutdownGracefully();
        isConnection = false;
    }

    /**
     * 设置连接状态
     * @param connection
     */
    public void setConnection(boolean connection){
        this.isConnection = connection;
    }

    /**
     * 退出
     */
    public void logout(){
        Presence logout = new Presence();
        logout.setType(Presence.Type.unavailable);
        try {
            writeAndFlush(logout, null);
        } catch (ConnectException e) {
            LOGGER.info(e.getMessage(),e);
        }
    }

    public ChannelFuture writeAndFlush(String xmpp) throws ConnectException {
            LOGGER.info("xmpp [{}]",xmpp);
            if (!isConnection){
                throw new ConnectException("服务器连接断开");
            }
            if (StringUtils.isNotBlank(xmpp)){
              return  channelFuture.channel().writeAndFlush(xmpp.concat(limiter));
            }
        return null;
    }
    /**
     * 发送并推送到服务端
     * @param packet
     * @param call
     * @return
     * @throws ConnectException
     */
    public ChannelFuture writeAndFlush(Packet packet, Controller call) throws ConnectException {
        if (!isConnection){
            throw new ConnectException("服务器连接断开");
        }
        if (call != null){
            Util.setCallController(packet.getID(),call);
        }
        return  channelFuture.channel().writeAndFlush(packet);
    }
    public boolean isConnection(){
        return isConnection;
    }
}
