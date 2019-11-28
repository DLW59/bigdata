package com.dlw.bigdata.udf;

import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * @author dengliwen
 * @date 2019/1/21
 */
public class MyHiveUDF extends UDF {

    //重载evaluate方法
    public String evaluate(String s) {
        return s.toLowerCase();
    }

}
