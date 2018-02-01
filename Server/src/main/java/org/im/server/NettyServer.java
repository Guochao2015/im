package org.im.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.im.common.CommonUtils;
import org.im.common.packet.PacketDecoder;
import org.im.common.packet.PacketEncoder;
import org.im.context.SpringApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class NettyServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServer.class);

    Integer port = 5222;

    Integer backlog  = 128;

    Integer ioThreadNum = 128;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    @Autowired
    private ExecutorService executorService;

    @PostConstruct
    public void startNetty() throws InterruptedException {
        LOGGER.info("start Netty");
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup(ioThreadNum);

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,backlog)
                .option(ChannelOption.SO_RCVBUF,1024) //接受缓冲区
                .option(ChannelOption.SO_SNDBUF,1024) //发送缓冲区
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .childOption(ChannelOption.TCP_NODELAY,true)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new IdleStateHandler(10,0,0, TimeUnit.SECONDS))//心跳检测
                                .addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer(CommonUtils.LIMITER.getBytes("UTF-8"))))//为了解决粘包
                                .addLast(new StringEncoder(Charset.forName("UTF-8")))
                                .addLast(new StringDecoder(Charset.forName("UTF-8")))
                                .addLast(new PacketDecoder(CommonUtils.LIMITER))
                                .addLast(new PacketEncoder(CommonUtils.LIMITER))
                                .addLast((ChannelHandler) SpringApplicationContext.getBean("handler"))
                        ;

                    }
                });
        ChannelFuture future = bootstrap.bind(port).sync();
        executorService.execute(()->{
            try {
                future.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage(),e);
            }
        });
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                stopNetty();
            }
        });
    }
    @PreDestroy
    public void stopNetty(){
        LOGGER.info("netty stop");
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }

    public static void main(String[] args) throws InterruptedException {
        NettyServer server = new NettyServer();
        server.startNetty();
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                server.stopNetty();
            }
        });
    }
}
