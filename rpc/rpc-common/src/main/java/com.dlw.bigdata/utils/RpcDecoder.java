package com.dlw.bigdata.utils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author dlw
 * @date 2018/8/18
 * @desc
 */
public class RpcDecoder extends ByteToMessageDecoder {

    private Class<?> aClass;

    public RpcDecoder(Class<?> aClass) {
        this.aClass = aClass;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 4) {
            return;
        }

        byteBuf.markReaderIndex();
        int length = byteBuf.readInt();
        if (length < 0) {
            channelHandlerContext.close();
        }
        if (byteBuf.readableBytes() < length) {
            byteBuf.resetReaderIndex();
        }
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        Object o = SerializationUtil.deserialize(bytes, aClass);
        list.add(o);
    }
}
