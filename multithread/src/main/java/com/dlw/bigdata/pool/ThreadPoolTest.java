package com.dlw.bigdata.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author dlw
 * @date 2018/8/16
 * @desc
 */
public class ThreadPoolTest {
    private static Future<Object> future;
    private static CountDownLatch latch = new CountDownLatch(1);
    public static void main(String[] args) throws Exception {
        //获取本机处理器线程数 我的是16
        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println(Runtime.getRuntime().availableProcessors());

        ExecutorService executor = Executors.newFixedThreadPool(processors);
        future = executor.submit(new MyCallable());
        handleFuture(future);
        executor.shutdown();
    }

    /**
     * 处理数据
     * @param future
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void handleFuture(Future<Object> future) throws ExecutionException, InterruptedException {
        System.out.println("阻塞主线程");
        latch.await();
        Object o = future.get();
        if (o instanceof List) {
            List<Integer> list = (List<Integer>) o;
            list.forEach(i ->System.out.print(i));
        }else {
            System.out.println("类型错误");
        }
    }
    private static class MyCallable implements Callable<Object>{
        List<Integer> list;
        @Override
        public Object call() throws Exception {
            list = new ArrayList<>();
            for (int i=0;i<5;i++) {
                list.add(i);
            }
            Thread.sleep(10000L);
            System.out.println("释放主线程");
            latch.countDown();
            return list;
        }
    }

}
