package com.dlw.bigdata.config;

import com.dlw.bigdata.bolt.FilterBolt;
import com.dlw.bigdata.spout.UserSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.kafka.*;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Collections;


/**
 * spring加载完后自动提交Topology
 *
 * @author dlw
 */
@Configuration
@Component
public class AutoLoad implements ApplicationListener<ContextRefreshedEvent> {


    private static String BROKERZKADDR;
    private static String TOPIC;
    private static String HOSTS;
    private static String PORT;

    public AutoLoad(@Value("${storm.brokerZkAddr}") String brokerZkaddr,
                    @Value("${zookeeper.hosts}") String hosts,
                    @Value("${zookeeper.port}") String port,
                    @Value("${spring.kafka.template.default-topic}") String topic
    ) {
        BROKERZKADDR = brokerZkaddr;
        HOSTS = hosts;
        TOPIC = topic;
        PORT = port;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            //实例化topologyBuilder类。
            TopologyBuilder topologyBuilder = new TopologyBuilder();
            //设置喷发节点并分配并发数，该并发数将会控制该对象在集群中的线程数。
//            BrokerHosts brokerHosts = new ZkHosts(BROKERZKADDR);
            // 配置Kafka订阅的Topic，以及zookeeper中数据节点目录和名字
//            SpoutConfig spoutConfig = new SpoutConfig(brokerHosts, TOPIC,
//                    "/storm", "1");
//            spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
//            spoutConfig.zkServers = Collections.singletonList(HOSTS);
//            spoutConfig.zkPort = Integer.parseInt(PORT);
            //从Kafka最新输出日志读取
//            KafkaSpout receiver = new KafkaSpout(spoutConfig);

            topologyBuilder.setSpout("users-spout", new UserSpout(), 1).setNumTasks(2);
            topologyBuilder.setBolt("filter-user-bolt", new FilterBolt(), 1).setNumTasks(2).shuffleGrouping("users-spout");
            Config config = new Config();
            config.setDebug(false);
            /*设置该topology在storm集群中要抢占的资源slot数，一个slot对应这supervisor节点上的以个worker进程
             如果你分配的spot数超过了你的物理节点所拥有的worker数目的话，有可能提交不成功，加入你的集群上面已经有了
             一些topology而现在还剩下2个worker资源，如果你在代码里分配4个给你的topology的话，那么这个topology可以提交
             但是提交以后你会发现并没有运行。 而当你kill掉一些topology后释放了一些slot后你的这个topology就会恢复正常运行。
            */
            config.setNumWorkers(1);
            //本地模式
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("myTopology", config, topologyBuilder.createTopology());
            //集群模式
//            StormSubmitter.submitTopology("myTopology", config, topologyBuilder.createTopology());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
