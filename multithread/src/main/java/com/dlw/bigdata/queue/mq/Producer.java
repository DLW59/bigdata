package com.dlw.bigdata.queue.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * author dlw
 * date 2018/10/1.
 * 生产者
 */
public class Producer<T> {
    private static final Logger log = LoggerFactory.getLogger(Producer.class);

    private static Producer producer = new Producer();

    public static Producer getInstance() {
        return producer;
    }

    public void produce(T t) throws InterruptedException {
        log.info("生产数据：{}",t);
        Broker.produce(t);
    }


}
