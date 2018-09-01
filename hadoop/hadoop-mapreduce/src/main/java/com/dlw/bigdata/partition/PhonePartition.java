package com.dlw.bigdata.partition;

import com.dlw.bigdata.bean.Flow;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * author dlw
 * date 2018/8/29.
 * 按照电话分区
 */
public class PhonePartition extends Partitioner<Text,Flow> {
    private static final Logger log = LoggerFactory.getLogger(PhonePartition.class);
    private static Map<String,Integer> map = new HashMap<>();
    static {
        map.put("13",1);
        map.put("15",2);
        map.put("17",3);
        map.put("18",4);
        log.info("map初始化数据：{}",map.toString());
    }

    @Override
    public int getPartition(Text text, Flow flow, int i) {
        String s = text.toString();
        String substring = s.substring(0, 2);
        return map.get(substring) == null ? 0 : map.get(substring);
    }
}
