package com.dlw.bigdata.driver;

import com.dlw.bigdata.mapper.WordCountMapper;
import com.dlw.bigdata.reducer.WordCountReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author dlw
 * @date 2018/8/28
 * @desc
 *  相当于一个yarn集群的客户端
 * 需要在此封装我们的mr程序的相关运行参数，指定jar包
 * 最后提交给yarn
 */
public class WordCountDriver {

    private static final String INPUT = "hdfs://hadoop01:9000/wordcount/input/wordcount.txt";
    private static final String OUTPUT = "hdfs://hadoop01:9000/wordcount/output";
    public static void main(String[] args) throws Exception {
        Job job = Job.getInstance(new Configuration());
//        job.setJar("/home/hadoop/wc.jar");
        //指定本程序的jar包所在的本地路径
        job.setJarByClass(WordCountDriver.class);

        //指定本业务job要使用的mapper/Reducer业务类
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        //指定mapper输出数据的kv类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        //指定最终输出的数据的kv类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        //指定job的输入原始文件所在目录
        FileInputFormat.setInputPaths(job, new Path(INPUT));
        //指定job的输出结果所在目录
        FileOutputFormat.setOutputPath(job, new Path(OUTPUT));
        //将job中配置的相关参数，以及job所用的java类所在的jar包，提交给yarn去运行
//        job.submit();
        boolean res = job.waitForCompletion(true);
        System.exit(res?0:1);
    }
}
