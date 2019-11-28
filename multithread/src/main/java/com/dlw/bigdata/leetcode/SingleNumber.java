package com.dlw.bigdata.leetcode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author dengliwen
 * @date 2019/3/5
 *
 * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
 *
 * 说明：
 *
 * 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
 *
 * 示例 1:
 *
 * 输入: [2,2,1]
 * 输出: 1
 * 示例 2:
 *
 * 输入: [4,1,2,1,2]
 * 输出: 4
 */
public class SingleNumber {

    public static void main(String[] args) {
        int[] a  = {1,2,2,1,3};
        long s = System.currentTimeMillis();
        System.out.println(singleNumber(a));
        System.out.println(System.currentTimeMillis() - s + "毫秒");
    }


    /**
    执行用时22ms 效率低下
     */
    public static int singleNumber(int[] nums) {

        int len = nums.length;
        Map<Integer,Integer> map = new HashMap<>();
        for (int i=0;i<len;i++){
            int key = nums[i];
            if (map.containsKey(key)){
                map.remove(nums[i]);
                continue;
            }
            map.put(nums[i],i);
        }
        Iterator<Integer> iterator = map.keySet().iterator();
        int result = 0;
        while (iterator.hasNext()) {
            result = iterator.next();
        }
        return result;
    }

    /**
     * leedcode 效率很高的答案 用时1ms 通过异或运算符
     * @param nums
     * @return
     */
    public static int singleNumber2(int[] nums){
        int single = 0;
        for(int i = 0;i < nums.length; i++){
            single = single ^ nums[i];
        }
        return single;
    }

}
