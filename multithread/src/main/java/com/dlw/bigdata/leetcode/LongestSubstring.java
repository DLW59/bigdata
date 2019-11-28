package com.dlw.bigdata.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * @author dengliwen
 * @date 2019/2/23
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 *
 * 输入: "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 */
public class LongestSubstring {

    public static void main(String[] args) {
        String s = "abcdbdfsbf";
        System.out.println(lengthSub(s));
    }

    private static int lengthSub(String s) {
        if (s == null || s.trim().length() == 0){
            return 0;
        }
        Set<Character> set = new HashSet<>();
        char c;
        int i = 0,j = 0,n = s.length(),max = 0;
        for (; i < n && j < n ; ) {
            c = s.charAt(j);
            if (set.contains(c)) {
                set.remove(s.charAt(i++));
            }else {
                set.add(c);
                j++;
                max = Math.max(max, j-i);
            }
        }
        return max;
    }
}
