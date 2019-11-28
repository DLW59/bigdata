package com.dlw.bigdata.lock;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {
	static final int SHARED_SHIFT   = 16;
	static final int SHARED_UNIT    = (1 << SHARED_SHIFT);
	static final int MAX_COUNT      = (1 << SHARED_SHIFT) - 1;
	static final int EXCLUSIVE_MASK = (1 << SHARED_SHIFT) - 1;

	private static final int COUNT_BITS = Integer.SIZE - 3;
	private static final int CAPACITY   = (1 << COUNT_BITS) - 1;
	private static final int RUNNING    = -1 << COUNT_BITS;
	private static final int SHUTDOWN   =  0 << COUNT_BITS;
	private static final int STOP       =  1 << COUNT_BITS;
	private static final int TIDYING    =  2 << COUNT_BITS;
	private static final int TERMINATED =  3 << COUNT_BITS;
	private static final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));

	public static void main(String[] args) {
//		int num = Runtime.getRuntime().availableProcessors();
//		System.out.println(num);
		System.out.println(1<<16);
		System.out.println("读锁："+sharedCount(1<<31));
		System.out.println("写锁:"+exclusiveCount(1));

		int a = 1,b=3;
		System.out.println("与" + (a & b));
		System.out.println("或" + (a | b));
		/**
		 * 异或：
		 * 0 0 0 1
		 * 			=>0 0 1 0=>2
		 * 0 0 1 1
		 * 相等的为0不相等为1
		 */
		System.out.println("异或"+(a ^ b));
		/**
		 * 先对b取非
		 * b=>~b
		 * 0 0 1 1 => 1 1 0 0
		 * 3=>12
		 * 再a&~b
		 * 0 0 0 1
		 * 			=>0 0 0 0=>0
		 * 1 1 0 0
		 */
		System.out.println(a & ~b);

		System.out.println(runStateOf(1));
		System.out.println(workerCountOf(1));
		System.out.println(ctlOf(SHUTDOWN, workerCountOf(ctl.get())));
		System.out.println(ctl.get());

		int i = 0;
		//retry 用于跳出外出循环
		retry:
		for (;;) {
			i++;
			int j = 0;
			for (;;) {
				j++;
				if (j ==2) {
					break retry;
				}
			}
//			if (i == 2) {
//				break;
//			}
//			System.out.println("i="+i);
		}
		System.out.println("i=" + i);


		Integer i1 =  1;
		Integer i2 = 2;
		System.out.println(i1 == i2);
	}

	static int sharedCount(int c)    {
		return c >>> SHARED_SHIFT;
	}

	static int exclusiveCount(int c) {
		return c & EXCLUSIVE_MASK;
	}

	private static int runStateOf(int c)     { return c & ~CAPACITY; }
	private static int workerCountOf(int c)  { return c & CAPACITY; }
	private static int ctlOf(int rs, int wc) { return rs | wc; }

}
