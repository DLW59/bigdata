package com.dlw.bigdata.proxy;

import com.dlw.bigdata.ServerDiscovery;
import com.dlw.bigdata.client.RpcClient;
import com.dlw.bigdata.model.RpcRequest;
import com.dlw.bigdata.model.RpcResponse;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * @author dlw
 * @date 2018/8/19
 * @desc 动态代理类
 */
public class RpcProxy {
    private ServerDiscovery serverDiscovery;

    public static RpcProxy newRpcProxy() {
        return new RpcProxy();
    }
    public RpcProxy() {
    }

    public RpcProxy(ServerDiscovery serverDiscovery) {
        this.serverDiscovery = serverDiscovery;
    }

    /**
     * 创建代理类
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T getProxy(Class<T> tClass) {
        Object object = Proxy.newProxyInstance(tClass.getClassLoader(), new Class[]{tClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return handle(method,args);
            }
        });
        return (T) object;
    }

    /**
     * 处理请求
     * @param method
     * @return
     */
    private Object handle(Method method,Object[] args) throws Exception {
        //封装请求数据
        RpcRequest rpcRequest = RpcRequest.builder().className(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args)
                .paramsType(method.getParameterTypes())
                .requestId(UUID.randomUUID().toString())
                .build();
        String serverAddr = null;
        if (null != serverDiscovery) {
            serverAddr = serverDiscovery.discovery();
        }
        if (StringUtils.isEmpty(serverAddr)) {
            return null;
        }
        String[] split = serverAddr.split(":");
        //创建Netty实现的RpcClient，链接服务端
        RpcClient rpcClient = new RpcClient(split[0],Integer.parseInt(split[1]));
        RpcResponse rpcResponse = rpcClient.send(rpcRequest);
        return rpcResponse.getData();

    }
}
