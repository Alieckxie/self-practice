package com.alieckxie.self.algorithm;

import java.util.Arrays;

import org.junit.Test;

public class QuickSortCVersionTest {

	@Test
	public void testQuickSortCVersion() {
		int[] array = QuickSortTest.generateRandomArray();
		System.out.println("========"+Arrays.toString(array));
		quicksort(array, 0, array.length - 1);
		System.out.println("========"+Arrays.toString(array));
	}

	public void quicksort(int[] array, int left, int right) {
		int pivot_index; // 基准的索引
		if (left < right) {
			System.out.println("开始进行中枢轴处理：###");
			pivot_index = partition(array, left, right);
			System.out.println("********"+Arrays.toString(array));
			System.out.println("开始处理左侧数据：+++");
			quicksort(array, left, pivot_index - 1);
			System.out.println("开始处理右侧数据：---");
			quicksort(array, pivot_index + 1, right);
		}

	}

	public int partition(int[] array, int left, int right) {
		int pivot = array[right]; // 选择最后一个元素作为基准
		int tail = left - 1; // tail为小于基准的子数组最后一个元素的索引
		for (int i = left; i < right; i++) { // 遍历基准以外的其他元素
			if (array[i] <= pivot) { // 把小于等于基准的元素放到前一个子数组中
				System.out.println(i + "位 " + array[i] + "<=" + pivot + "把小于等于基准的元素放到前一个子数组中");
				tail++;
//				swap(array, tail, i);
			}
		}
		swap(array, tail + 1, right); // 最后把基准放到前一个子数组的后边,剩下的子数组既是大于基准的子数组
		// 该操作很有可能把后面元素的稳定性打乱,所以快速排序是不稳定的排序算法
		return tail + 1; // 返回基准的索引
	}

	public void swap(int[] array, int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
		System.out.print(j + "位：" + array[i] + " 与 " + i + "位：" + + array[j] + "互换 ");
		System.out.println(Arrays.toString(array));
	}

	public <T> void swap(T[] array, int i, int j) {
		T temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
	
	public <T extends Comparable<? super T>> T[] quickSort1(T[] targetArr, int start, int end) {
		int i = start + 1, j = end;
		T key = targetArr[start];

		if (start >= end)
			return (targetArr);

		/*
		 * 从i++和j--两个方向搜索不满足条件的值并交换
		 *
		 * 条件为：i++方向小于key，j--方向大于key
		 */
		while (true) {
			while (targetArr[j].compareTo(key) > 0)
				j--;
			while (targetArr[i].compareTo(key) < 0 && i < j)
				i++;
			if (i >= j)
				break;
			swap(targetArr, i, j);
			if (targetArr[i] == key) {
				j--;
			} else {
				i++;
			}
		}

		/* 关键数据放到‘中间’ */
		swap(targetArr, start, j);

		if (start < i - 1) {
			this.quickSort1(targetArr, start, i - 1);
		}
		if (j + 1 < end) {
			this.quickSort1(targetArr, j + 1, end);
		}

		return targetArr;
	}
	
	public <T extends Comparable<? super T>> void quickSort2(T[] targetArr, int start, int end) {
		int i = start, j = end;
		T key = targetArr[start];

		while (i < j) {
			/* 按j--方向遍历目标数组，直到比key小的值为止 */
			while (j > i && targetArr[j].compareTo(key) >= 0) {
				j--;
			}
			if (i < j) {
				/* targetArr[i]已经保存在key中，可将后面的数填入 */
				targetArr[i] = targetArr[j];
				i++;
			}
			/* 按i++方向遍历目标数组，直到比key大的值为止 */
			while (i < j && targetArr[i].compareTo(key) <= 0)
			/* 此处一定要小于等于零，假设数组之内有一亿个1，0交替出现的话，而key的值又恰巧是1的话，那么这个小于等于的作用就会使下面的if语句少执行一亿次。 */
			{
				i++;
			}
			if (i < j) {
				/* targetArr[j]已保存在targetArr[i]中，可将前面的值填入 */
				targetArr[j] = targetArr[i];
				j--;
			}
		}
		/* 此时i==j */
		targetArr[i] = key;

		/* 递归调用，把key前面的完成排序 */
		this.quickSort2(targetArr, start, i - 1);

		/* 递归调用，把key后面的完成排序 */
		this.quickSort2(targetArr, j + 1, end);

	}

}
