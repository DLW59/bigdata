package com.dlw.bigdata.driver;

import com.dlw.bigdata.bean.Flow;
import com.dlw.bigdata.combiner.FlowCombiner;
import com.dlw.bigdata.enums.CalWay;
import com.dlw.bigdata.mapper.FlowMapper;
import com.dlw.bigdata.mapper.FlowSortMapper;
import com.dlw.bigdata.partition.PhonePartition;
import com.dlw.bigdata.reducer.FlowReducer;
import com.dlw.bigdata.reducer.FlowSortReducer;
import org.apache.commons.lang.ArrayUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author dlw
 * @date 2018/8/28
 * @desc
 */
public class FlowDriver {
    private static final Logger log = LoggerFactory.getLogger(FlowDriver.class);
    private static final String INPUT = "hdfs://hadoop01:9000/flow/input/flow.dat";
    private static final String OUTPUT = "hdfs://hadoop01:9000/flow/output";

    public static void main(String[] args) throws Exception {
        if (ArrayUtils.isEmpty(args)) {
            args[0] = INPUT;
            args[1] = OUTPUT;
        }
        Configuration conf = new Configuration();

        //可以配置本地模式  可以用本地文件和hdfs文件系统
//        conf.set("mapreduce.framework.name","local");
//        conf.set("fs.defaultFS","hdfs://hadoop01:9000");
//        conf.set("fs.defaultFS","file:///");
//        conf.set("hadoop.tmp.dir","F:\\hadoop-2.6.5\\data");

        //运行集群模式在yarn上用以下配置
//        conf.set("mapreduce.framework.name","yarn");
//        conf.set("yarn.resourcemanager.hostname","hadoop01");
//        conf.set("fs.defaultFS","hdfs://hadoop01:9000");

        Job job = Job.getInstance(conf);
//        job.setJar("/home/hadoop/workspace/apps/data/mapreduce/hadoop-mapreduce-1.0-SNAPSHOT.jar");
        //指定本程序的jar包所在的本地路径
        job.setJarByClass(FlowDriver.class);

        //自定义分区
        setCalWay(CalWay.PARTITION, job);
        //如果不设置默认TextInputFormat
        job.setInputFormatClass(CombineTextInputFormat.class);
        CombineTextInputFormat.setMinInputSplitSize(job,1024*1024*2);//2M
        CombineTextInputFormat.setMaxInputSplitSize(job,1024*1024*10);//10M
        //指定job的输入原始文件所在目录
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        //指定job的输出结果所在目录
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        //将job中配置的相关参数，以及job所用的java类所在的jar包，提交给yarn去运行

        FileSystem fileSystem = FileSystem.get(conf);
        Path path = new Path(args[1]);
        if (fileSystem.exists(path)) {
            fileSystem.delete(path,true);
        }
        boolean res = job.waitForCompletion(true);
        System.exit(res ? 0 : 1);
//                job.submit();
    }

    public static void setCalWay(CalWay calWay,Job job) {
        switch (calWay) {
            case SORT:
                log.info("排序方式");
                setSortJob(job);
                break;
            case PARTITION:
                log.info("分区方式");
                setPartitionJob(job);
                break;
            default:
                log.info("默认方式");
                setDefaultJob(job);
                break;
        }
    }

    private static void setPartitionJob(Job job) {

        job.setPartitionerClass(PhonePartition.class);
        //指定本业务job要使用的mapper/Reducer业务类
        job.setMapperClass(FlowMapper.class);
        job.setReducerClass(FlowReducer.class);

        //指定mapper输出数据的kv类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Flow.class);

        //指定最终输出的数据的kv类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Flow.class);
        job.setCombinerClass(FlowCombiner.class);
        job.setNumReduceTasks(5);
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
