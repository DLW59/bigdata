package com.dlw.bigdata.executors;

import com.dlw.bigdata.schedule.LoginLogTask;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author dengliwen
 * @date 2019/1/25
 * 线程池
 */
@Component
public class ExecutorTask {

    private static LoginLogTask logTask = new LoginLogTask();

    /**
     * 根据时间执行
     */
    public void execute4Time(int second) {
        int processors = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(processors);
        ((ScheduledThreadPoolExecutor) executor).scheduleAtFixedRate(logTask, second,1, TimeUnit.SECONDS);
    }

}
