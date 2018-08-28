package com.dlw.bigdata.mapper;

import com.dlw.bigdata.bean.Flow;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author dlw
 * @date 2018/8/28
 * @desc 流量統計mapper
 */
public class FlowSortMapper extends Mapper<LongWritable,Text,Flow,Text> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String s = value.toString();
        String[] split = s.split("\t");
        Text phone = new Text(split[1]);
        long up = Long.parseLong(split[split.length - 3]);
        long down = Long.parseLong(split[split.length - 2]);
        context.write(new Flow(up,down,up + down), phone);
    }
}
