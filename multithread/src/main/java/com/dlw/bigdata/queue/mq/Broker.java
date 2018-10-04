package com.dlw.bigdata.queue.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * author dlw
 * date 2018/10/1.
 * 模拟mq的broker
 */
public class Broker {
    private static final Logger log = LoggerFactory.getLogger(Broker.class);

    private static final int MAX_SIZE = 100;

    public static BlockingQueue queue = new ArrayBlockingQueue(MAX_SIZE);

    /**
     * 生产
     * @param o
     * @throws InterruptedException
     */
    public static void produce(Object o) throws InterruptedException {
        if (isNull(o)){
            log.warn("produce object is null ");
            return;
        }
        queue.put(o);
    }

    /**
     * 消费
     * @return
     * @throws InterruptedException
     */
    public static Object consume() throws InterruptedException {
        return queue.take();
    }

    private static boolean isNull(Object o) {
        if (Objects.isNull(o)) {
            return true;
        }
        return false;
    }
}
