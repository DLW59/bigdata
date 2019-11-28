package com.dlw.bigdata.jvm;


import java.util.*;

/**
 * @author dengliwen
 * @date 2019/2/14
 * 栈内存溢出列子
 * vm 参数设置：-Xss128k
 * 调优：加大Xss值
 */
public class StackOverFlow {

    public static void main(String[] args) {
        f();
    }

    public static void f(){
        HashMap<String,Object> map = new HashMap<>();
        TreeMap m = new TreeMap();
        List list1 = new LinkedList();
        List list2 = new ArrayList();
        Set set1 = new HashSet();
        Set set2 = new TreeSet();
        Object put = map.put("1", 1);
        Object put2 = map.put("1", 2);
        System.out.println(put2);
        System.out.println(map.get("1"));
//        f();
    }

}
