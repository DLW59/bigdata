package com.dlw.bigdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author dlw
 * @date 2018/9/8
 * @desc
 */
@SpringBootApplication
@EnableTransactionManagement
@ComponentScan("com.dlw.bigdata")
@EnableKafka
public class StormKafkaApplication extends SpringBootServletInitializer {
    private ApplicationContext context;
    private static volatile StormKafkaApplication stormKafkaApplication;
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(StormKafkaApplication.class, args);
        StormKafkaApplication application = new StormKafkaApplication();
        application.setApplicationContext(context);
        setStormLauncher(application);
    }

    /**
     * 设置上下文
     * @param context
     */
    private void setApplicationContext(ApplicationContext context) {
        this.context = context;
    }

    /**
     * 获取上下文
     */
    private ApplicationContext setApplicationContext() {
        return this.context;
    }

    private static void setStormLauncher(StormKafkaApplication stormKafkaApplication) {
        StormKafkaApplication.stormKafkaApplication = stormKafkaApplication;
    }

    public static StormKafkaApplication getStormLauncher() {
        return stormKafkaApplication;
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(StormKafkaApplication.class);
    }

    /**
     * 通过自定义name获取 实例 Bean.
     *
     * @param name the name
     * @return the bean
     */
    public Object getBean(String name) {
        return context.getBean(name);
    }

    /**
     * 通过class获取Bean.
     *
     * @param <T>   the type parameter
     * @param clazz the clazz
     * @return the bean
     */
    public <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param <T>   the type parameter
     * @param name  the name
     * @param clazz the clazz
     * @return the bean
     */
    public <T> T getBean(String name, Class<T> clazz) {
        return context.getBean(name, clazz);
    }

}
