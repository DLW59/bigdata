package com.dlw.bigdata.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dengliwen
 * @date 2019/2/14
 * 常量池溢出  相当于永久区溢出
 * 永久区存放常量，静态变量，class等
 * vm参数设置：（-XX:PermSize=10M -XX:MaxPermSize=10M）-Xmx10m -Xms10m -XX:+PrintGCDetails
 * 调优：加大永久区大小
 * 在jdk1.7之前，字符串常量存储在方法区的PermGen Space。在jdk1.7之后，字符串常量重新被移到了堆中。
 * String.intern()解析：https://www.cnblogs.com/Kidezyq/p/8040338.html
 */
public class ConstantOutOfMemory {

    public static void main(String[] args) {
        try {

            List<String> strings = new ArrayList<>();

            int i = 0;

            while (true) {

                strings.add(String.valueOf(i++).intern());

            }

        } catch (Exception e) {

            e.printStackTrace();

            throw e;

        }
    }
}

/**
 * 检测jdk1.7后常量池存储在堆中，配置-XX:PermSize=10M -XX:MaxPermSize=10M无效，改为-Xmx10m -Xms10m得到以下日志
 *
 * [Full GC (Ergonomics) Exception in thread "main" java.lang.OutOfMemoryError: GC overhead limit exceeded
 * [PSYoungGen: 2047K->0K(2560K)] [ParOldGen: 7065K->808K(7168K)] 9113K->808K(9728K), [Metaspace: 3370K->3370K(1056768K)], 0.0056690 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
 * Heap
 *  PSYoungGen      total 2560K, used 55K [0x00000000ffd00000, 0x0000000100000000, 0x0000000100000000)
 *   eden space 2048K, 2% used [0x00000000ffd00000,0x00000000ffd0de28,0x00000000fff00000)
 *   from space 512K, 0% used [0x00000000fff80000,0x00000000fff80000,0x0000000100000000)
 *   to   space 512K, 0% used [0x00000000fff00000,0x00000000fff00000,0x00000000fff80000)
 *  ParOldGen       total 7168K, used 808K [0x00000000ff600000, 0x00000000ffd00000, 0x00000000ffd00000)
 *   object space 7168K, 11% used [0x00000000ff600000,0x00000000ff6ca1f8,0x00000000ffd00000)
 *  Metaspace       used 3377K, capacity 4496K, committed 4864K, reserved 1056768K
 *   class space    used 363K, capacity 388K, committed 512K, reserved 1048576K
 * 	at java.lang.Integer.toString(Integer.java:401)
 * 	at java.lang.String.valueOf(String.java:3086)
 * 	at com.dlw.bigdata.jvm.ConstantOutOfMemory.main(ConstantOutOfMemory.java:29)
 */
