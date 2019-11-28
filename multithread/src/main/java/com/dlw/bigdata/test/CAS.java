package com.dlw.bigdata.test;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;

/**
 * @author dengliwen
 * @date 2019/7/15
 * @desc
 */
public class CAS {

    public static void main(String[] args) throws Exception {

        final IntUnaryOperator operator = new IntUnaryOperator() {
            @Override
            public int applyAsInt(int operand) {
                System.out.println(operand);
                return operand * 2;
            }
        };
        final int i = operator.applyAsInt(1);
        System.out.println(i);

        IntBinaryOperator binaryOperator = (left, right) -> left + right;
        final int i1 = binaryOperator.applyAsInt(1, 1);
        System.out.println(i1);
        AtomicInteger integer = new AtomicInteger();
        integer.set(3);
        final Field field = integer.getClass().getDeclaredField("valueOffset");
        field.setAccessible(true);
        final Object o = field.get("valueOffset");//计算在内存中的位置
        System.out.println(o);

        integer.incrementAndGet();
        System.out.println(field.get("valueOffset"));


        AtomicIntegerArray array = new AtomicIntegerArray(2);
        System.out.println(array.get(0));
        Unsafe unsafe = Unsafe.getUnsafe();
        final int base = unsafe.arrayBaseOffset(int[].class);
        System.out.println(base);
    }


}
