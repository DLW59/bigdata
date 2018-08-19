package com.dlw.bigdata.service;

import com.dlw.bigdata.bean.Person;

/**
 * @author dlw
 * @date 2018/8/19
 * @desc
 */
public interface PersonService {

    String getInfo(String name,int age);

    String getPerson(Person person);

}
