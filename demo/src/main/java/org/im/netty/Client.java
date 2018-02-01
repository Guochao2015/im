package org.im.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

public class Client {
    public static void main(String[] args) throws Exception {

        EventLoopGroup workgroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(workgroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        sc.pipeline().addLast(new ChannelHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//                                super.channelRead(ctx, msg);
                                try {
                                    //do something msg
                                    ByteBuf buf = (ByteBuf)msg;
                                    byte[] data = new byte[buf.readableBytes()];
                                    buf.readBytes(data);
                                    String request = new String(data, "utf-8");
                                    System.out.println("Client: " + request);


                                } finally {
                                    ReferenceCountUtil.release(msg);
                                }
                            }

                            @Override
                            public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                                super.channelReadComplete(ctx);
                            }

                            @Override
                            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                super.exceptionCaught(ctx, cause);
                            }
                        });
                    }
                });

        ChannelFuture cf1 = b.connect("127.0.0.1", 5222).sync();

        //buf
        cf1.channel().writeAndFlush(Unpooled.copiedBuffer("777".getBytes()));

        cf1.channel().closeFuture().sync();
        workgroup.shutdownGracefully();

    }
}
