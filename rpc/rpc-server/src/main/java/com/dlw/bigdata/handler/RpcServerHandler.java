package com.dlw.bigdata.handler;


import com.dlw.bigdata.enums.RpcResponseCode;
import com.dlw.bigdata.model.RpcRequest;
import com.dlw.bigdata.model.RpcResponse;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author dlw
 * @date 2018/8/18
 * @desc
 */
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcRequest> {
    private static final Logger log = LoggerFactory.getLogger(RpcServerHandler.class);
    private Map<String,Object> map;
    public RpcServerHandler(Map<String,Object> map) {
        this.map = map;
    }
    /**
     * 接收消息，处理消息，返回结果
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequest rpcRequest) throws Exception {
        RpcResponse rpcResponse = new RpcResponse();
        try {
            Object o = map.get(rpcRequest.getClassName());
            String methodName = rpcRequest.getMethodName();
            Object[] params = rpcRequest.getParams();
            Class<?>[] paramsType = rpcRequest.getParamsType();
            String requestId = rpcRequest.getRequestId();
            Method method = o.getClass().getMethod(methodName, paramsType);
            Object result = method.invoke(o, params);
            rpcResponse.setData(result);
        }catch (Exception e) {
            rpcResponse.setStatus(RpcResponseCode.ERROR.code);
            rpcResponse.setMsg(RpcResponseCode.ERROR.msg);
        }
        //写入 outbundle（即RpcEncoder）进行下一步处理（即编码）后发送到channel中给客户端
        channelHandlerContext.writeAndFlush(rpcResponse).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(cause.getMessage());
        ctx.close();
    }
}
