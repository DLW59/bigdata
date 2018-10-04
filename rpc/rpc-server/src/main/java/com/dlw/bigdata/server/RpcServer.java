package com.dlw.bigdata.server;

import com.dlw.bigdata.ServerRegister;
import com.dlw.bigdata.annotation.RpcService;
import com.dlw.bigdata.handler.RpcServerHandler;
import com.dlw.bigdata.model.RpcRequest;
import com.dlw.bigdata.model.RpcResponse;
import com.dlw.bigdata.utils.RpcDecoder;
import com.dlw.bigdata.utils.RpcEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dlw
 * @date 2018/8/18
 * @desc
 */
public class RpcServer implements ApplicationContextAware,InitializingBean{
    private static final Logger log = LoggerFactory.getLogger(RpcServer.class);
    //netty连接地址
    private String nettyAddr;
    //netty端口
    private Integer nettyPort;
    //服务注册对象
    private ServerRegister serverRegister;
    //存放扫描的带有RpcServer注解的类名（K）和对象(V)
    private Map<String,Object> map = new HashMap<>();

    public RpcServer() {
    }

    public RpcServer(String nettyAddr, int nettyPort, ServerRegister serverRegister) {
        this.nettyAddr = nettyAddr;
        this.nettyPort = nettyPort;
        this.serverRegister = serverRegister;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> objectMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        if (objectMap.isEmpty()) {
            return;
        }
        log.info(objectMap.keySet().toString());
        Collection<Object> values = objectMap.values();
        values.forEach(object -> {
            String name = object.getClass().getAnnotation(RpcService.class).value().getName();
            map.put(name,object );
        });
        log.info("添加了rpcservice注解的类信息：{}",map);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = null;
        try {
            serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss,worker )
                    .channel(NioServerSocketChannel.class)
                    .localAddress(nettyAddr,nettyPort )
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new RpcDecoder(RpcRequest.class));
                            socketChannel.pipeline().addLast(new RpcEncoder(RpcResponse.class));
                            socketChannel.pipeline().addLast(new RpcServerHandler(map));
                        }
                    })
                    .childOption(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = serverBootstrap.bind(nettyAddr, nettyPort).sync();
            log.info("server started on address:port {}:{}", nettyAddr,nettyPort);
            if (null != serverRegister) {
                serverRegister.register(nettyAddr+":"+nettyPort);
            }
            future.channel().closeFuture().sync();
        }finally {
            worker.shutdownGracefully().sync();
            boss.shutdownGracefully().sync();
        }
    }
}
