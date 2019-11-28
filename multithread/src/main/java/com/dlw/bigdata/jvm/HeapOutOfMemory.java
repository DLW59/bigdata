package com.dlw.bigdata.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dengliwen
 * @date 2019/2/14
 * 堆内存溢出列子
 * vm参数设置:-Xmx20m -Xms20m -XX:+PrintGCDetails
 * 调优：加大最大内存和初始内存的值
 */
public class HeapOutOfMemory {

    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();

        while (true) {
            list.add(new Object());
        }
    }
}


/** 日志打印
 * "C:\Program Files\Java\jdk1.8.0_25\bin\java.exe" -Xms20M -Xmx20M -XX:+PrintGCDetails "-javaagent:C:\idea\IntelliJ IDEA 2018.2.5\lib\idea_rt.jar=56799:C:\idea\IntelliJ IDEA 2018.2.5\bin" -Dfile.encoding=UTF-8 -classpath "C:\Program Files\Java\jdk1.8.0_25\jre\lib\charsets.jar;C:\Program Files\Java\jdk1.8.0_25\jre\lib\deploy.jar;C:\Program Files\Java\jdk1.8.0_25\jre\lib\ext\access-bridge-64.jar;C:\Program Files\Java\jdk1.8.0_25\jre\lib\ext\cldrdata.jar;C:\Program Files\Java\jdk1.8.0_25\jre\lib\ext\dnsns.jar;C:\Program Files\Java\jdk1.8.0_25\jre\lib\ext\jaccess.jar;C:\Program Files\Java\jdk1.8.0_25\jre\lib\ext\jfxrt.jar;C:\Program Files\Java\jdk1.8.0_25\jre\lib\ext\localedata.jar;C:\Program Files\Java\jdk1.8.0_25\jre\lib\ext\nashorn.jar;C:\Program Files\Java\jdk1.8.0_25\jre\lib\ext\sunec.jar;C:\Program Files\Java\jdk1.8.0_25\jre\lib\ext\sunjce_provider.jar;C:\Program Files\Java\jdk1.8.0_25\jre\lib\ext\sunmscapi.jar;C:\Program Files\Java\jdk1.8.0_25\jre\lib\ext\sunpkcs11.jar;C:\Program Files\Java\jdk1.8.0_25\jre\lib\ext\zipfs.jar;C:\Program Files\Java\jdk1.8.0_25\jre\lib\javaws.jar;C:\Program Files\Java\jdk1.8.0_25\jre\lib\jce.jar;C:\Program Files\Java\jdk1.8.0_25\jre\lib\jfr.jar;C:\Program Files\Java\jdk1.8.0_25\jre\lib\jfxswt.jar;C:\Program Files\Java\jdk1.8.0_25\jre\lib\jsse.jar;C:\Program Files\Java\jdk1.8.0_25\jre\lib\management-agent.jar;C:\Program Files\Java\jdk1.8.0_25\jre\lib\plugin.jar;C:\Program Files\Java\jdk1.8.0_25\jre\lib\resources.jar;C:\Program Files\Java\jdk1.8.0_25\jre\lib\rt.jar;F:\workspace\local\bigdata\multithread\target\classes;F:\apache-maven-3.3.9\.m2\org\apache\activemq\activemq-all\5.15.5\activemq-all-5.15.5.jar;F:\apache-maven-3.3.9\.m2\io\netty\netty-all\4.1.28.Final\netty-all-4.1.28.Final.jar;F:\apache-maven-3.3.9\.m2\org\springframework\boot\spring-boot-devtools\2.0.2.RELEASE\spring-boot-devtools-2.0.2.RELEASE.jar;F:\apache-maven-3.3.9\.m2\org\springframework\boot\spring-boot\2.0.2.RELEASE\spring-boot-2.0.2.RELEASE.jar;F:\apache-maven-3.3.9\.m2\org\springframework\spring-context\5.0.6.RELEASE\spring-context-5.0.6.RELEASE.jar;F:\apache-maven-3.3.9\.m2\org\springframework\spring-aop\5.0.6.RELEASE\spring-aop-5.0.6.RELEASE.jar;F:\apache-maven-3.3.9\.m2\org\springframework\spring-beans\5.0.6.RELEASE\spring-beans-5.0.6.RELEASE.jar;F:\apache-maven-3.3.9\.m2\org\springframework\spring-expression\5.0.6.RELEASE\spring-expression-5.0.6.RELEASE.jar;F:\apache-maven-3.3.9\.m2\org\springframework\boot\spring-boot-autoconfigure\2.0.2.RELEASE\spring-boot-autoconfigure-2.0.2.RELEASE.jar;F:\apache-maven-3.3.9\.m2\org\springframework\spring-core\5.0.6.RELEASE\spring-core-5.0.6.RELEASE.jar;F:\apache-maven-3.3.9\.m2\org\springframework\spring-jcl\5.0.6.RELEASE\spring-jcl-5.0.6.RELEASE.jar" com.dlw.bigdata.jvm.HeapOutOfMemory
 * [GC (Allocation Failure) [PSYoungGen: 5421K->504K(6144K)] 5421K->3000K(19968K), 0.0106636 secs] [Times: user=0.00 sys=0.00, real=0.03 secs]
 * [GC (Allocation Failure) [PSYoungGen: 6136K->496K(6144K)] 8632K->7023K(19968K), 0.0055256 secs] [Times: user=0.09 sys=0.02, real=0.01 secs]
 * [GC (Allocation Failure) [PSYoungGen: 6128K->504K(6144K)] 12655K->12645K(19968K), 0.0073279 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
 * [Full GC (Ergonomics) [PSYoungGen: 504K->0K(6144K)] [ParOldGen: 12141K->10699K(13824K)] 12645K->10699K(19968K), [Metaspace: 3341K->3341K(1056768K)], 0.1433494 secs] [Times: user=0.42 sys=0.00, real=0.14 secs]
 * [Full GC (Ergonomics) [PSYoungGen: 5632K->511K(6144K)] [ParOldGen: 10699K->13694K(13824K)] 16331K->14206K(19968K), [Metaspace: 3341K->3341K(1056768K)], 0.1018297 secs] [Times: user=0.23 sys=0.00, real=0.10 secs]
 * [Full GC (Ergonomics) [PSYoungGen: 3107K->2969K(6144K)] [ParOldGen: 13694K->13691K(13824K)] 16801K->16661K(19968K), [Metaspace: 3341K->3341K(1056768K)], 0.1178744 secs] [Times: user=0.53 sys=0.03, real=0.12 secs]
 * [Full GC (Allocation Failure) [PSYoungGen: 2969K->2969K(6144K)] [ParOldGen: 13691K->13662K(13824K)] 16661K->16632K(19968K), [Metaspace: 3341K->3341K(1056768K)], 0.1144492 secs] [Times: user=0.31 sys=0.03, real=0.11 secs]
 * Heap
 *  PSYoungGen      total 6144K, used 3128K [0x00000000ff980000, 0x0000000100000000, 0x0000000100000000)
 *   eden space 5632K, 55% used [0x00000000ff980000,0x00000000ffc8e3a8,0x00000000fff00000)
 *   from space 512K, 0% used [0x00000000fff00000,0x00000000fff00000,0x00000000fff80000)
 *   to   space 512K, 0% used [0x00000000fff80000,0x00000000fff80000,0x0000000100000000)
 *  ParOldGen       total 13824K, used 13662K [0x00000000fec00000, 0x00000000ff980000, 0x00000000ff980000)
 *   object space 13824K, 98% used [0x00000000fec00000,0x00000000ff957b88,0x00000000ff980000)
 *  Metaspace       used 3373K, capacity 4496K, committed 4864K, reserved 1056768K
 *   class space    used 363K, capacity 388K, committed 512K, reserved 1048576K
 * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
 * 	at java.util.Arrays.copyOf(Arrays.java:3210)
 * 	at java.util.Arrays.copyOf(Arrays.java:3181)
 * 	at java.util.ArrayList.grow(ArrayList.java:261)
 * 	at java.util.ArrayList.ensureExplicitCapacity(ArrayList.java:235)
 * 	at java.util.ArrayList.ensureCapacityInternal(ArrayList.java:227)
 * 	at java.util.ArrayList.add(ArrayList.java:458)
 * 	at com.dlw.bigdata.jvm.HeapOutOfMemory.main(HeapOutOfMemory.java:19)
 *
 * Process finished with exit code 1
 */
