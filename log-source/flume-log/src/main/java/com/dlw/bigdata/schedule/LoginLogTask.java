package com.dlw.bigdata.schedule;

import com.dlw.bigdata.controller.LoginController;
import com.dlw.bigdata.domain.Log;
import com.dlw.bigdata.domain.LoginLog;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Random;
import java.util.UUID;

/**
 * @author dengliwen
 * @date 2019/1/25
 * 登录定时任务
 */

@Component
public class LoginLogTask implements Runnable{

    private static LoginController controller  = new LoginController();
    private static Random random = new Random();
    private static String[] names = {"jack","rose","dlw","mj","张三"};
    private static Timestamp timestamp;
    private static LoginLog log = new LoginLog();

    @Override
    public void run() {
        timestamp = new Timestamp(System.currentTimeMillis());
        log.setIp("localhost");
        log.setPhone("110");
        log.setSex(random.nextInt(2) >1 ? "男": "女" );
        log.setUsername(names[random.nextInt(names.length)]);
        log.setLogId(UUID.randomUUID().toString());
        log.setLogType(Log.LogTypeEnum.LOGIN);
        log.setCreateTime(timestamp);
        controller.login(log);
    }

}
