package com.dlw.bigdata.handler;

import com.dlw.bigdata.model.RpcRequest;
import com.dlw.bigdata.model.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author dlw
 * @date 2018/8/19
 * @desc
 */
public class RpcClientHandler extends SimpleChannelInboundHandler<RpcResponse> {
    private RpcRequest rpcRequest;
    private RpcResponse rpcResponse;
    public RpcClientHandler() {
    }
    public RpcClientHandler(RpcRequest rpcRequest) {
        this.rpcRequest = rpcRequest;
    }

    // • 从服务器接收到数据后调用
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponse rpcResponse) throws Exception {
        this.rpcResponse = rpcResponse;
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.write(rpcRequest);
        ctx.flush();
    }
    public RpcResponse getRpcResponse() {
        return rpcResponse;
    }
}
