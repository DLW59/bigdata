package com.dlw.bigdata.netty.demo1.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * author dlw
 * date 2018/9/20.
 * 服务器入站数据handler
 */
//标示一个ChannelHandler 可以被多个 Channel 安全地共享
@ChannelHandler.Sharable
public class HelloServerInBoundHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        String s = new String(bytes, "utf-8");
        System.out.println("server receive：" + s);

        //将消息发送给发送者
        ByteBuf buf = Unpooled.copiedBuffer(s.getBytes());
        ctx.writeAndFlush(buf);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将未决消息冲刷到远程节点，并且关闭该 Channel
//        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        String message = cause.getMessage();
        System.out.println(message);
        ctx.close();
    }
}
