package com.dlw.bigdata.controller;

import com.dlw.bigdata.domain.LoginLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dengliwen
 * @date 2019/1/22
 */
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    /**
     * 生成登录日志
     */
    public void login(LoginLog loginLog) {
        log.info(loginLog.toString());
    }

}
