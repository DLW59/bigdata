package com.dlw.bigdata.consumer;

import com.dlw.bigdata.domain.User;
import com.dlw.bigdata.producer.UserProducer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author dlw
 * @date 2018/9/8
 * @desc
 */
@Component
public class UserConsumer extends BaseConsumer<String,User> {
    private static final Logger log = LoggerFactory.getLogger(UserConsumer.class);
    @Override
    @KafkaListener(topics = {"test"})
    public void consume(ConsumerRecord<String, User> record) {
        Optional<User> value = Optional.ofNullable(record.value());
        if (value.isPresent()) {
            User user = value.get();
            log.info("消费数据：{}",user.toString());
            collection.add(user);
            //TODO 发送到spout做数据源
        }
    }
}
