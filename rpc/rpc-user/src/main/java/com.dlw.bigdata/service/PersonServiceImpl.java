package com.dlw.bigdata.service;

import com.dlw.bigdata.annotation.RpcService;
import com.dlw.bigdata.bean.Person;
import com.dlw.bigdata.proxy.RpcProxy;
import com.dlw.bigdata.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dlw
 * @date 2018/8/19
 * @desc
 */
@Service
@RpcService(PersonService.class)
public class PersonServiceImpl implements PersonService {

    @Autowired
    private RpcProxy rpcProxy;
    @Override
    public String getInfo(String name, int age) {
        PersonService personService = rpcProxy.getProxy(PersonService.class);
        String s = personService.getInfo(name, age);
        return name+":"+age;
    }

    @Override
    public String getPerson(Person person) {
        return null;
    }
}
