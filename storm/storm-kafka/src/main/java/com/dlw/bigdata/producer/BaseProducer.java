package com.dlw.bigdata.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author dlw
 * @date 2018/9/8
 * @desc
 */
public abstract class BaseProducer<K,V> {

    /**
     * 发送消息
     */
    public abstract void send(K k,V v);
}
