package com.dlw.bigdata.queue.mq;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.*;

/**
 * author dlw
 * date 2018/10/1.
 */
@Slf4j
public class MqTest {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        ExecutorService executor = new ThreadPoolExecutor(10,50,60L,TimeUnit.SECONDS,new ArrayBlockingQueue<>(50));
        try {
            Thread producer = new Thread(() -> {
                try {
                    Producer.getInstance().produce(new Message(UUID.randomUUID().toString(),String.valueOf(System.currentTimeMillis())));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "producer-thread");

            Thread consumer = new Thread(() -> {
                try {
                    Consumer.getInstance().consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "consumer-thread");
            executor.execute(consumer);
            executor.execute(producer);
            executor.execute(producer);
            executor.execute(producer);
            if (Broker.queue.isEmpty()) {
                Thread.sleep(1000L);
                latch.countDown();
            }
        } finally {
            executor.shutdown();
        }

        latch.await();

        log.info("队列里的数据数量：{}",Broker.queue.size());
    }
}
