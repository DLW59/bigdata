package com.dlw.bigdata.pool;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dengliwen
 * @date 2019/8/13
 * @desc
 */
public class ExecutorTest {

    private ExecutorService executor = Executors.newFixedThreadPool(5);
    private Map<Integer,String> map = new ConcurrentHashMap<>();
    public static void main(String[] args) {
        ExecutorTest test = new ExecutorTest();
        test.run();
    }

    public void run() {
       for (int i=0;i<5;i++) {
           int finalI = i;
//           executor.submit(() -> {
//               try {
//                   print(finalI);
//               } catch (InterruptedException e) {
//                   e.printStackTrace();
//               }
//           });
           executor.submit(new Task(i,map));
           System.out.println("#"+map);
           System.out.println("任务" + i + "执行中");
       }
       executor.shutdown();
    }

    public  void print(int i) throws InterruptedException {
        Set<Integer> set = new HashSet<>();
        switch (i) {
            case 0:
                set.add(i);
                break;
            case 1:
                set.add(i);
                break;
            case 2:
                set.add(i);
                break;
            case 3:
                set.add(i);
                break;
            case 4:
                set.add(i);
                break;
        }
//        TimeUnit.SECONDS.sleep(1L);
        List<Integer> list = new ArrayList<>(set);
        System.out.println(list);
    }

}

class Task implements Runnable {

    private static AtomicInteger taskNum = new AtomicInteger(0);
    private Map<Integer,String> map = new ConcurrentHashMap<>();
    private int i;
    public Task(int i,Map<Integer,String> map) {
        this.i = i;
        this.map = map;
    }
    @Override
    public void run() {
        System.out.println("任务" + i +"的当前任务数是" +taskNum.incrementAndGet());
        map.putIfAbsent(i,i+"" );
//        map.put(i, i+"");
//        System.out.println(map);
    }
}
