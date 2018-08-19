package com.dlw.bigdata.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author dlw
 * @date 2018/8/19
 * @desc
 */
@Data
@Component
@ConfigurationProperties(prefix = "zookeeper")
public class ZookeeperConfig {
    private String zookeeperConnectString;
    private String persistentPath;
    private String tempPath;
    private int timeout;
}
