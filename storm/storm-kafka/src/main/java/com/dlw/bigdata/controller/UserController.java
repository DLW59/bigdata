package com.dlw.bigdata.controller;

import com.dlw.bigdata.domain.User;
import com.dlw.bigdata.producer.UserProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dlw
 * @date 2018/9/8
 * @desc
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController{
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserProducer userProducer;

    @Value("${spring.kafka.template.default-topic}")
    private String topic;

    @PostMapping(value = "/add",produces = "application/json;charset=utf-8")
    public void addUser(@RequestBody User user) {
        if (verifyParams(user)) {
            log.info("参数有误====user:{}",user);
        }
        userProducer.send(topic,user );
    }
}
