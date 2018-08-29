package com.dlw.bigdata.driver;

import com.dlw.bigdata.bean.Flow;
import com.dlw.bigdata.enums.CalWay;
import com.dlw.bigdata.mapper.FlowMapper;
import com.dlw.bigdata.mapper.FlowSortMapper;
import com.dlw.bigdata.reducer.FlowReducer;
import com.dlw.bigdata.reducer.FlowSortReducer;
import org.apache.commons.lang.ArrayUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author dlw
 * @date 2018/8/28
 * @desc
 */
public class FlowDriver {
    private static final String INPUT = "hdfs://hadoop01:9000/flow/input/flow.dat";
    private static final String OUTPUT = "hdfs://hadoop01:9000/flow/output";

    public static void main(String[] args) throws Exception {
        if (ArrayUtils.isEmpty(args)) {
            args[0] = INPUT;
            args[1] = OUTPUT;
        }
        Job job = Job.getInstance(new Configuration());
//        job.setJar("/home/hadoop/wc.jar");
        //指定本程序的jar包所在的本地路径
        job.setJarByClass(FlowDriver.class);

        setCalWay(CalWay.SORT, job);
        //指定job的输入原始文件所在目录
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        //指定job的输出结果所在目录
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        //将job中配置的相关参数，以及job所用的java类所在的jar包，提交给yarn去运行
//        job.submit();
        boolean res = job.waitForCompletion(true);
        System.exit(res ? 0 : 1);
    }

    public static void setCalWay(CalWay calWay,Job job) {
        switch (calWay) {
            case SORT:
                setSortJob(job);
                break;
            case PARTITION:
                setDefaultJob(job);
                break;
            default:
                setDefaultJob(job);
                break;
        }
    }

    private static void setSortJob(Job job) {
        //指定本业务job要使用的mapper/Reducer业务类
        job.setMapperClass(FlowSortMapper.class);
        job.setReducerClass(FlowSortReducer.class);

        //指定mapper输出数据的kv类型
        job.setMapOutputKeyClass(Flow.class);
        job.setMapOutputValueClass(Text.class);

        //指定最终输出的数据的kv类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Flow.class);
    }

    private static void setDefaultJob(Job job) {
        //指定本业务job要使用的mapper/Reducer业务类
        job.setMapperClass(FlowMapper.class);
        job.setReducerClass(FlowReducer.class);

        //指定mapper输出数据的kv类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Flow.class);

        //指定最终输出的数据的kv类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Flow.class);

    }
}
