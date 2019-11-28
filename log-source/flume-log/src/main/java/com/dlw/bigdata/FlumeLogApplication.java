package com.dlw.bigdata;

import com.dlw.bigdata.domain.LoginLog;
import com.dlw.bigdata.executors.ExecutorTask;
import com.dlw.bigdata.schedule.LoginLogTask;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.security.PrivateKey;

/**
 * @author dengliwen
 * @date 2019/1/25
 */
@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableCaching
public class FlumeLogApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(FlumeLogApplication.class, args);
        ExecutorTask task = context.getBean(ExecutorTask.class);
        task.execute4Time(1);
    }

}
