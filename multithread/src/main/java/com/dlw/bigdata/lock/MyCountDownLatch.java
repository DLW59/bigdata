package com.dlw.bigdata.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author dengliwen
 * @date 2019/7/24
 * @desc
 */
public class MyCountDownLatch {

    private static CountDownLatch latch = new CountDownLatch(5);
    private static ExecutorService executor = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws Exception {

        List<Future<?>> futures = new ArrayList<>();
        for (int i=0;i<5;i++) {
//            new Thread(new Worker(latch),"thread-" + i).start();
            final Future<?> future = executor.submit(new Worker(latch));
            futures.add(future);
        }
        latch.await();
        final Future<?> future = futures.get(0);
        final Object o = future.get();
        System.out.println(null == o ? "null" : o.toString());
        System.out.println("执行完毕");
        executor.shutdown();
    }


    private static class Worker implements Runnable {

        private CountDownLatch latch;

        public Worker(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "正在执行");
            latch.countDown();
        }
    }
}
