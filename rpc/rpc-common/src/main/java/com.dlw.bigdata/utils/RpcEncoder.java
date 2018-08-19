package com.dlw.bigdata.utils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author dlw
 * @date 2018/8/18
 * @desc 编码工具类
 */
public class RpcEncoder extends MessageToByteEncoder {

    private Class<?> aClass;

    public RpcEncoder(Class<?> aClass) {
        this.aClass = aClass;
    }
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        if (aClass.isInstance(o)) {
            byte[] bytes = SerializationUtil.serialize(o);
            byteBuf.writeInt(bytes.length);
            byteBuf.writeBytes(bytes);
        }


    }
}
