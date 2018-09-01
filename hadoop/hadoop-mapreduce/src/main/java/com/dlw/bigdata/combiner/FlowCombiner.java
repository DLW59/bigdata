package com.dlw.bigdata.combiner;

import com.dlw.bigdata.bean.Flow;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * author dlw
 * date 2018/8/29.
 * 局部reduce 不能影响业务最终的结果
 * 比如求平均数不能用combiner
 */
public class FlowCombiner extends Reducer<Text,Flow,Text,Flow> {

    Flow flow = null;
    @Override
    protected void reduce(Text key, Iterable<Flow> values, Context context) throws IOException, InterruptedException {
        System.out.println("执行combiner....");
        flow = Flow.newInstance();
        AtomicLong up = new AtomicLong(0L);
        AtomicLong down = new AtomicLong(0L);
        values.forEach(flow -> {
            up.addAndGet(flow.getUp());
            down.addAndGet(flow.getDown());
        });
        flow.setUp(up.get());
        flow.setDown(down.get());
        flow.setTotal(up.get() + down.get());
        context.write(key,this.flow);
    }
}
