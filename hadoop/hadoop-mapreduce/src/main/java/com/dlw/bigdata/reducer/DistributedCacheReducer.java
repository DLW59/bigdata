package com.dlw.bigdata.reducer;

import com.dlw.bigdata.bean.OrderProduct;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author dlw
 * @date 2018/9/1
 * @desc
 */
public class DistributedCacheReducer extends Reducer<Text,OrderProduct,Text,OrderProduct> {
    private static final Logger log = LoggerFactory.getLogger(DistributedCacheReducer.class);
    @Override
    protected void reduce(Text key, Iterable<OrderProduct> values, Context context) throws IOException, InterruptedException {
        OrderProduct next = null;
        while (values.iterator().hasNext()) {
            next = values.iterator().next();
        }
        assert next != null;
        log.info("join之后的数据：{}",next.toString());
        context.write(key,next );
    }
}
