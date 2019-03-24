package com.alieckxie.self.algorithm.sort;

import java.util.Arrays;

import org.junit.Test;

import com.alieckxie.self.algorithm.util.MiscUtil;

public class InsertSortTest {

	@Test
	public void testSimpleInsertSort() {
		for (int i = 0; i < 10; i++) {
			Integer[] array = MiscUtil.generateRandomIntegerArray(10, 100, 10);
			Integer[] clone = array.clone();
			System.out.println(Arrays.toString(array));
			// insertSortWithHeadSentinel(array);
			insertSort(array);
			System.out.println(Arrays.toString(array));
			Arrays.sort(clone);
			System.out.println(Arrays.toString(clone));
			boolean equals = Arrays.equals(array, clone);
			System.out.println(equals + "===================");
		}
	}

	/**
	 * 其实吧，有时候哨兵这种东西，不用也罢。
	 * 工程上讲究一个代码的可维护性，建立一个头哨徒增代码复杂性。
	 * 毕竟把数组下标为0的给空出来另作他用非常违背一般程序员的认知的。
	 * @param targetArr
	 */
	public <T extends Comparable<? super T>> void insertSort(T[] targetArr) {
		for (int i = 0; i < targetArr.length - 1; i++) {
			if (targetArr[i + 1].compareTo(targetArr[i]) < 0) {
				T tmpTarget = targetArr[i + 1];
				int j;
				for (j = i; j >= 0 && tmpTarget.compareTo(targetArr[j]) < 0; j--) {
					targetArr[j + 1] = targetArr[j];
				}
				targetArr[j + 1] = tmpTarget;
			}
		}
	}

	public <T extends Comparable<? super T>> void insertSortWithHeadSentinel(T[] targetArr) {
		for (int i = 0; i < targetArr.length - 1; i++) {
			if (targetArr[i + 1].compareTo(targetArr[i]) < 0) {
				T tmpTarget = targetArr[i + 1];
				T tmpHead = null;
				// 目标大于头元素，不会出现数组下标越界的情况，直接循环处理
				if (targetArr[i + 1].compareTo(targetArr[0]) <= 0) {
					// 目标小于等于头元素，暂存头元素，目标覆盖到0号位，在循环结束后放置头元素放在1号位
					tmpHead = targetArr[0];
					targetArr[0] = targetArr[i + 1];
				}
				int j;
				for (j = i; tmpTarget.compareTo(targetArr[j]) < 0; j--) {
					targetArr[j + 1] = targetArr[j];
				}
				if (tmpHead != null) {
					targetArr[j + 1] = tmpHead;
				} else {
					targetArr[j + 1] = tmpTarget;
				}
			}
		}
	}
}
