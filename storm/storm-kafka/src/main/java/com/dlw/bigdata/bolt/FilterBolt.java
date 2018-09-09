package com.dlw.bigdata.bolt;

import com.dlw.bigdata.StormKafkaApplication;
import com.dlw.bigdata.domain.User;
import com.dlw.bigdata.service.UserService;
import org.apache.storm.topology.*;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dlw
 * @date 2018/9/8
 * @desc
 */
public class FilterBolt extends BaseBasicBolt{

    private StormKafkaApplication stormKafkaApplication;
    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        List<Object> values = tuple.getValues();
        Object o = tuple.getValueByField("users-spout");
        List<User> list = (List<User>) o;
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        //过滤年龄小于18岁的用户
        list = list.stream().filter(user -> user.getAge() > 18)
                .collect(Collectors.toList());
//        basicOutputCollector.emit(new Values(list));
        this.stormKafkaApplication = StormKafkaApplication.getStormLauncher();
        UserService userService = stormKafkaApplication.getBean(UserService.class);
        //添加到数据库
        userService.batchAdd(list);

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("filter-user-bolt"));
    }
}
