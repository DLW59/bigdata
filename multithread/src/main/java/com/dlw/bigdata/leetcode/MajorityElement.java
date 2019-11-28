package com.dlw.bigdata.leetcode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author dengliwen
 * @date 2019/3/5
 *
 * 给定一个大小为 n 的数组，找到其中的众数。众数是指在数组中出现次数大于 ⌊ n/2 ⌋ 的元素。
 *
 * 你可以假设数组是非空的，并且给定的数组总是存在众数。
 *
 * 示例 1:
 *
 * 输入: [3,2,3]
 * 输出: 3
 * 示例 2:
 *
 * 输入: [2,2,1,1,1,2,2]
 * 输出: 2
 */
public class MajorityElement {

    public static void main(String[] args) {
        int[] a = {3,3,4};
        long s = System.currentTimeMillis();
        System.out.println(majorityElement(a));
        System.out.println(System.currentTimeMillis() - s + "毫秒");
    }

    public static int majorityElement(int[] nums) {
        int floor = (int)Math.floor((double) nums.length/2);
        Map<Integer,Integer> map = new HashMap<>();
        int len = nums.length;
        for (int i=0;i<len;i++){
            int key = nums[i];
            if (map.containsKey(key)){
                int value = map.get(key);
                if (value > floor || value + 1 > floor) {
                    return key;
                }
                map.put(key, value + 1);
            }else {
                map.put(key, 1);
            }
        }
        Iterator<Integer> iterator = map.keySet().iterator();
        int next = 0;
        while (iterator.hasNext()) {
            next = iterator.next();
            if (map.get(next) > floor){
                return next;
            }
        }
        return next;
    }
}
