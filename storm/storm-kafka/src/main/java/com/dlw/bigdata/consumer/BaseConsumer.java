package com.dlw.bigdata.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListeners;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author dlw
 * @date 2018/9/8
 * @desc
 */
public abstract class BaseConsumer<K,V> {

    public Collection<V> collection;
    /**
     * 消费消息
     */
    public abstract void consume(ConsumerRecord<K,V> record);
}
