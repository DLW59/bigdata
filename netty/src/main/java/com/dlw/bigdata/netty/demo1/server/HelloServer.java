package com.dlw.bigdata.netty.demo1.server;


import com.dlw.bigdata.netty.demo1.server.handler.HelloServerInBoundHandler;
import com.sun.corba.se.internal.CosNaming.BootstrapServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * author dlw
 * date 2018/9/20.
 */
public class HelloServer {

    private static EventLoopGroup boss;
    private static EventLoopGroup worker;
    private static ServerBootstrap server;

    public static void run() throws InterruptedException {
        HelloServerInBoundHandler handler = new HelloServerInBoundHandler();
        boss = new NioEventLoopGroup();
        worker = new NioEventLoopGroup();
        server = new ServerBootstrap();
        try {
            server.group(boss,worker)
                    .channel(NioServerSocketChannel.class)
                    .localAddress("localhost",10000)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            socketChannel.pipeline().addLast(handler);//in
                        }
                    });
            ChannelFuture future = server.bind().sync();
            future.channel().closeFuture().sync();
        }catch (Exception e){
            worker.shutdownGracefully().sync();
            boss.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {
        run();
    }
}
