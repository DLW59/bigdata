package com.dlw.bigdata.spout;

import com.dlw.bigdata.StormKafkaApplication;
import com.dlw.bigdata.consumer.UserConsumer;
import com.dlw.bigdata.domain.User;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * @author dlw
 * @date 2018/9/8
 * @desc
 */
public class UserSpout extends BaseRichSpout {
    private static final Logger log = LoggerFactory.getLogger(UserSpout.class);
    @Autowired
    private UserConsumer userConsumer;
    private SpoutOutputCollector collector;

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
    }

    @Override
    public void nextTuple() {
        Utils.sleep(100L);
        StormKafkaApplication application = StormKafkaApplication.getStormLauncher();
        UserConsumer consumer = application.getBean(UserConsumer.class);
        log.info("注入的userConsumer：{}",userConsumer);
        log.info("getBean获得的userConsumer：{}",consumer);
        Collection<User> collection = consumer.collection;
//        collection.forEach(user -> collector.emit(new Values(user)));
        collector.emit(Collections.singletonList(collection));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("users-spout"));
    }
}
