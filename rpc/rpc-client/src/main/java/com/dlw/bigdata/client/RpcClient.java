package com.dlw.bigdata.client;

import com.dlw.bigdata.ServerDiscovery;
import com.dlw.bigdata.handler.RpcClientHandler;
import com.dlw.bigdata.model.RpcRequest;
import com.dlw.bigdata.model.RpcResponse;
import com.dlw.bigdata.utils.RpcDecoder;
import com.dlw.bigdata.utils.RpcEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;

/**
 * @author dlw
 * @date 2018/8/19
 * @desc
 */
public class RpcClient extends SimpleChannelInboundHandler<RpcResponse> {
    private static final Logger log = LoggerFactory.getLogger(RpcClient.class);
    private String nettyAddr;
    private int nettyPort;
    private RpcResponse rpcResponse;
    private final Object obj = new Object();
    public RpcClient() {
    }

        public RpcClient(String nettyAddr, int nettyPort) {
        this.nettyAddr = nettyAddr;
        this.nettyPort = nettyPort;
    }

    /**
     * 发送请求 建立连接
     * @param rpcRequest
     * @return
     */
    public RpcResponse send(RpcRequest rpcRequest) throws Exception {
        EventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap bootstrap = null;
        try {
            bootstrap = new Bootstrap();
            bootstrap.group(worker)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new RpcEncoder(RpcRequest.class));
                            socketChannel.pipeline().addLast(new RpcDecoder((RpcResponse.class)));
                            socketChannel.pipeline().addLast(RpcClient.this);
                        }
                    })
                    .option(ChannelOption.SO_KEEPALIVE,true );
            // 链接服务器
            log.info("开始连接服务器...");
            ChannelFuture future = bootstrap.connect(nettyAddr,nettyPort).sync();
            //将request对象写入outbundle处理后发出（即RpcEncoder编码器）
            future.channel().writeAndFlush(rpcRequest).sync();
            // 用线程等待的方式决定是否关闭连接
            // 其意义是：先在此阻塞，等待获取到服务端的返回后，被唤醒，从而关闭网络连接
            synchronized (obj) {
                obj.wait();
            }
            if (null != rpcResponse) {
                future.channel().closeFuture().sync();
            }
        }finally {
            worker.shutdownGracefully().sync();
        }
        log.info("rpcResponse:{}",rpcResponse);
        return rpcResponse;
    }

    /**
     * 读取服务端的返回结果
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, RpcResponse rpcResponse)
            throws Exception {
        this.rpcResponse = rpcResponse;
        log.info("rpcResponse:{}",rpcResponse);
        synchronized (obj) {
            obj.notifyAll();
        }
    }
}
