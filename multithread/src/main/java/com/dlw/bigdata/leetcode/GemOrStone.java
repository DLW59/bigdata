package com.dlw.bigdata.leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author dengliwen
 * @date 2019/2/21
 * @desc 给定字符串J 代表石头中宝石的类型，和字符串 S代表你拥有的石头。
 * S 中每个字符代表了一种你拥有的石头的类型，你想知道你拥有的石头中有多少是宝石
 * J 中的字母不重复，J 和 S中的所有字符都是字母。字母区分大小写，因此"a"和"A"是不同类型的石头。
 */
public class GemOrStone {

    private static final String S = "aA";
    private static final String J = "aAAd";

    public static void main(String[] args) {
//        System.out.println(gemNum(S,J));
        System.out.println(gemNum());
    }


    /**
     * 方法一  效率低
     * @param s
     * @param j
     * @return
     */
    private static int gemNum(String s,String j){
        int count = 0;
        for(int i=0;i<j.length();i++){
            if(s.contains(String.valueOf(j.charAt(i)))) {
                count++;
            }
        }
        return count;
    }

    /**
     * 方法二 效率更低 笑哭
     * @return
     */
    private static int gemNum(){
        Map<Character,Integer> map = new HashMap<>();
        for (int i = 0; i < J.length(); i++) {
            char key = J.charAt(i);
            if (map.containsKey(key)){
                map.put(key, map.get(key)+1);
            }else {
                map.put(key, 1);
            }
        }

        int count = 0;
        for (int i=0;i<S.length();i++){
            char key = S.charAt(i);
            if (map.get(key) != null) {
                count+=map.get(key);
            }
        }
        Stack stack = new Stack();

        return count;

    }
}
