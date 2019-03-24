package com.alieckxie.self.algorithm.search;

import org.junit.Test;

public class SearchProblemTest {

	@Test
	public void testSearchHalfCountsElement() {
		int[] array = new int[] { 1, 2, 2, 2, 5, 2, 7, 2, 9 };
		int element = findTheElement(array);
		int data = findMostData(array);

		System.out.println(element);
		System.out.println(data);
	}

	int findTheElement(int[] array) { // 从大小为n的无序数组array中寻找出现次数超过半数的元素
		int theElement = 0; // the_element辅助变量用来存储当前未被匹配掉的元素
		int times, i; // times记录the_element未被匹配的个数
		for (i = 0, times = 0; i < array.length; i++) {
			if (times == 0) {
				theElement = array[i];
				times++;
			} else {
				if (theElement == array[i])
					times++;
				else
					times--;
			}
		}
		return theElement;
	}

	int findMostData(int arr[]) {
		int findNum = 0; // 出现次数超过一半的数；
		int count = 0; // 只要最终count > 0，那么对应的findNum就是出现次数超过一半的数；

		// 循环过程中，i每增加一次，就相当于把i之前的所有元素（除了满足“findNum ==
		// arr[i]”条件的arr[i]，这些对应元素用“count++”来标记）都抛弃了，但是之后每当执行一次“ count--
		// ”时，又会从前面这些已保留的且等于findNum的元素中删除一项，直到count == 0，再重选findNum；
		for (int i = 0; i < arr.length; i++) {
			if (count == 0) {// count为0时，表示当前的findNum需要重选；
				findNum = arr[i];
				count = 1;
			} else {
				if (findNum == arr[i])
					count++;
				else
					count--;
			}
		}

		return findNum;
	}

	@Test
	public void testFindMaxSubSeq() {
		int[] arr = new int[] {-2, 6, -3, 10, -5, 1, 10};
		System.out.println(maxSumUlt(arr));
		System.out.println(fun(arr, 6));
	}

	private int maxSumUlt(int[] arr) {
		int maxSum = 0xf0000000;
		int sum = 0;
		for (int i = 0; i < arr.length; ++i) {
			if (sum < 0) {
				sum = arr[i];
			} else {
				sum += arr[i];
			}
			if (sum > maxSum) {
				maxSum = sum;
			}
		}
		return maxSum;
	}
	
	private int[] b = new int[7];

	private int fun(int[] a, int n) {
		if (n < 0)
			return 0;
		if (b[n] > 0)
			return b[n];
		int t = fun(a, n - 1);
		if (t < 0) // 计算结果小于0，重置为当前元素
			b[n] = a[n];
		else
			b[n] = t + a[n];
		return b[n];
	}

}
