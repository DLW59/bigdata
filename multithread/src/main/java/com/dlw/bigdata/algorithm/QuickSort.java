package com.dlw.bigdata.algorithm;

/**
 * author dlw
 * date 2018/10/2.
 * 快速排序
 */
public class QuickSort {
    private static int[] a = {5, 2, 9, 3, 4, 11, 0, 8};

    public static void main(String[] args) {
        sort(a,0,a.length - 1);
    }

    public static int[] sort(int[] a,int start,int end) {
        int tmp;
        int target = a[start];
        if (a.length == 1) {
            return a;
        }else if (a.length == 2){
            if (target <= a[1]) {
                return a;
            }
            tmp = a[0];
            a[0] = a[1];
            a[1] = tmp;
            return a;
        }else {
            while (end > start) {
                if (a[end] < target) {
                    tmp = a[end];
                    a[end] = a[start];
                    a[start] = tmp;
                }
                end--;
                if (a[start] > target) {
                    tmp = a[start];
                    a[start] = target;
                    target = tmp;
                }
            }
        }

        return a;

}

}
