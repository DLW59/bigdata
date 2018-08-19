package com.dlw.bigdata.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author dlw
 * @date 2018/8/19
 * @desc
 */
@Data
@Component
@ConfigurationProperties(prefix = "netty")
public class NettyConfig {
    private String nettyAddr;
    private int nettyPort;
}
