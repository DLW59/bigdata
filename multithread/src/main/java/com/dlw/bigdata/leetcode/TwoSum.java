package com.dlw.bigdata.leetcode;

import java.util.*;

/**
 * @author dengliwen
 * @date 2019/2/13
 *
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，
 * 并返回他们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 *
 * 示例:
 *
 * 给定 nums = [2, 7, 11, 15], target = 9
 *
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 */
public class TwoSum {

    private static int[] arr = {3,2,3};

    public static void main(String[] args) {
        int[] a = twoSum(arr, 6);
        printArr(a);
    }

    public static int[] twoSum(int[] nums, int target) {
        if (nums.length == 0) {
            System.out.println("empty");
        }else if (nums.length < 2) {
            System.out.println("num too less");
        }else {
            int length = arr.length;
            if (target < arr[0] + arr[1] || target > arr[length-2] + arr[length-1]) {
                return new int[0];
            }
            int[] index = findIndex(arr, target);
            return index;

        }
        return new int[0];

    }

    /**
     * 类似折半查找
     * @param arr
     * @param target
     * @return
     * 暴力破解法 O(n^2)
     */
    private static int[] findIndex(int[] arr, int target) {
        List list = new ArrayList();
        int[] a = new int[2];
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] + arr[j] == target) {
                    a[0] = i;
                    a[1] = j;
                    break;
                }
            }
        }
       return a;
    }

    private static void printArr(int[] a) {
        if (a.length == 0) {
            System.out.println("[]");
        }
        StringBuilder builder = new StringBuilder("[");
        for (int i : a) {
            builder.append(i).append(",");
        }
        String substring = builder.toString().substring(0, builder.toString().length() - 1);
        substring += "]";
        substring.trim();
        System.out.println(substring);


    }
}
