package com.dlw.bigdata;

import com.dlw.bigdata.config.RpcConfig;
import com.dlw.bigdata.server.RpcServer;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author dlw
 * @date 2018/8/17
 * @desc rpc服务端
 */
//@SpringBootApplication
public class RpcServerStartApplication {
    private static final Logger log = LoggerFactory.getLogger(RpcServerStartApplication.class);
    public static void main(String[] args) {
        log.info("开始启动程序...");
//        SpringApplication.run(RpcServerStartApplication.class, args);
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");
        AnnotationConfigApplicationContext context1 = new AnnotationConfigApplicationContext();
        Object serverRegister = context.getBean("serverRegister");
        ServerRegister sr = (ServerRegister) serverRegister;
        log.info(sr.toString());
    }
}
