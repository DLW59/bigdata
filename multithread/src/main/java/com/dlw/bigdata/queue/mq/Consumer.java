package com.dlw.bigdata.queue.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * author dlw
 * date 2018/10/1.
 * 消费者
 */
public class Consumer {
    private static final Logger log = LoggerFactory.getLogger(Consumer.class);

    private static Consumer consumer = new Consumer();

    public static Consumer getInstance() {
        return consumer;
    }

    public Object consume() throws InterruptedException {
        Object  consume = null;
        while (true) {
            if (!Broker.queue.isEmpty()) {
                consume = Broker.consume();
                log.info("消费数据：{}",consume);

            }
//            return consume;

        }
    }

}
