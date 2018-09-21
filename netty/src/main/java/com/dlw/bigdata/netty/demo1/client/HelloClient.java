package com.dlw.bigdata.netty.demo1.client;

import com.dlw.bigdata.netty.demo1.client.handler.HelloClientInBoundHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * author dlw
 * date 2018/9/20.
 */
public class HelloClient {

    private static EventLoopGroup group;
    private static Bootstrap client;

    public static void main(String[] args) throws Exception{
        run();
    }

    private static void run() throws Exception{
        HelloClientInBoundHandler handler = new HelloClientInBoundHandler();
        group = new NioEventLoopGroup();
        client = new Bootstrap();
        try {
            client.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress("localhost",10000)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(handler);
                        }
                    });
            ChannelFuture future = client.connect().sync();
            future.channel().closeFuture().sync();
        }catch (Exception e) {
            group.shutdownGracefully().sync();
        }

    }
}
