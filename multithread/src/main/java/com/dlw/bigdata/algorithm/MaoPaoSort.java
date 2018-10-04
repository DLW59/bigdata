package com.dlw.bigdata.algorithm;

import java.util.Arrays;
import java.util.List;

import static java.lang.System.*;

/**
 * author dlw
 * date 2018/10/2.
 */
public class MaoPaoSort {

    public static void main(String[] args) {
        sort();
    }

    public static void sort() {
        int[] a = {5, 2, 9, 3, 4, 11, 0, 8};
//        list.sort(Integer::compareTo);
        for (int i=0;i<a.length-1;i++) {
            int tmp;
            for (int j=i;j<a.length;j++) {
                if (a[i] > a[j]) {
                    tmp = a[i];
                    a[i] = a[j];
                    a[j] = tmp;

                }
            }
        }

        for (int i : a) {
            out.println(i);
        }
    }
}
