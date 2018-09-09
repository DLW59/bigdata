package com.dlw.bigdata.producer;

import com.dlw.bigdata.config.AutoLoad;
import com.dlw.bigdata.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;

/**
 * @author dlw
 * @date 2018/9/8
 * @desc
 */
@Component
public class UserProducer extends BaseProducer<String,User> {
    private static final Logger log = LoggerFactory.getLogger(UserProducer.class);

    @Autowired
    private KafkaTemplate<String,User> kafkaTemplate;
    @Override
    public void send(String topic,User user) {
        ListenableFuture future = kafkaTemplate.send(topic, user);
        if (future.isDone()) {
            log.info("发送消息完成");
        }
    }
}
