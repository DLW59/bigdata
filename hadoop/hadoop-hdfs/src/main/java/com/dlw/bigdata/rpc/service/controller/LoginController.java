package com.dlw.bigdata.rpc.service.controller;

import com.dlw.bigdata.rpc.service.LoginServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author dlw
 * @date 2018/8/27
 * @desc 登录
 */
@Slf4j
public class LoginController {
    public static void main(String[] args) throws Exception {
        LoginServiceInterface login = RPC.getProxy(LoginServiceInterface.class, 2L,
                new InetSocketAddress("localhost", 9999), new Configuration());
        String s = login.login("乖乖", "123456");
        log.info(s);
    }
}
