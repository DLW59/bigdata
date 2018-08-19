package com.dlw.bigdata.config;

import com.dlw.bigdata.ServerRegister;
import com.dlw.bigdata.server.RpcServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author dlw
 * @date 2018/8/19
 * @desc
 */
@Configuration
@Slf4j
public class RpcConfig {

    @Resource
    private ZookeeperConfig zookeeperConfig;

    @Resource
    private NettyConfig nettyConfig;

    @Bean
//    @ConfigurationProperties(prefix = "zookeeper")
    public ServerRegister getServerRegister() {
        log.info("zk配置信息：{}",zookeeperConfig.toString());
        ServerRegister serverRegister = new ServerRegister(zookeeperConfig.getZookeeperConnectString(),
                zookeeperConfig.getPersistentPath(),zookeeperConfig.getTempPath(),zookeeperConfig.getTimeout());
        return serverRegister;
    }

    @Bean
//    @ConfigurationProperties(prefix = "netty")
    public RpcServer getRpcServer() {
        log.info("nettyConfig信息：{}",nettyConfig.toString());
        RpcServer rpcServer = new RpcServer(nettyConfig.getNettyAddr(),nettyConfig.getNettyPort(),getServerRegister());
        return rpcServer;
    }
}
