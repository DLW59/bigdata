package com.dlw.bigdata.netty.demo1.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;

/**
 * author dlw
 * date 2018/9/20.
 */
//标示一个ChannelHandler 可以被多个 Channel 安全地共享
@ChannelHandler.Sharable
public class HelloClientInBoundHandler extends SimpleChannelInboundHandler<ByteBuf> {

    /**
     * 在到服务器的连接已经建立之后将被调用；
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello server", CharsetUtil.UTF_8));
    }

    /**
     * 当从服务器接收到一条消息时被调用
     * @param channelHandlerContext
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
//        String s = new String(byteBuf.array(), CharsetUtil.UTF_8);
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        System.out.println("client receive:" + new String(bytes,"utf-8"));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("错误信息:"+cause);
        ctx.close();
    }
}
