package com.alieckxie.self.algorithm.sort;

import java.util.Arrays;

import org.junit.Test;

public class MergeSortTest {

	@Test
	public void testMergeSort() {
		int[] arr = { 9, 8, 7, 6, 5, 4, 3, 2, 1 };
		sort(arr);
		System.out.println(Arrays.toString(arr));
	}

	public static void sort(int[] arr) {
		int[] temp = new int[arr.length];// 在排序前，先建好一个长度等于原数组长度的临时数组，避免递归中频繁开辟空间
		sort(arr, 0, arr.length - 1, temp);
	}

	/**
	 * 进行递归二分（如果要多路归并就通过循环或者硬编码来实现更多路的归并）。
	 * 该递归先完成最左侧的归并划分，一直递归到0：
	 * <p>left:0 和 mid:4 开始进行排序……</p>
	 * <p>left:0 和 mid:2 开始进行排序……</p>
	 * <p>left:0 和 mid:1 开始进行排序……</p>
	 * <p>left:0 和 mid:0 开始进行排序……</p>
	 * <p>left:0 和 mid:0 已经排序完成！</p>
	 * <p>mid+1:1 和 right:1 开始进行排序……</p>
	 * 
	 * @param arr
	 * @param left
	 * @param right
	 * @param temp
	 */
	private static void sort(int[] arr, int left, int right, int[] temp) {
		if (left < right) {
			int mid = (left + right) / 2;
			System.out.println("left:" + left + " 和 mid:" + mid + " 开始进行排序……");
			sort(arr, left, mid, temp);// 左边归并排序，使得左子序列有序
			System.out.println("left:" + left + " 和 mid:" + mid + " 已经排序完成！");
			int mid1 = mid + 1;
			System.out.println("mid+1:" + mid1 + " 和 right:" + right + " 开始进行排序……");
			sort(arr, mid + 1, right, temp);// 右边归并排序，使得右子序列有序
			System.out.println("mid+1:" + mid1 + " 和 right:" + right + " 已经排序完成！");
			System.out.println("==================");
			System.out.println("left:" + left + " 和 mid:" + mid + " 和 right:" + right + "开始进行归并……");
			merge(arr, left, mid, right, temp);// 将两个有序子数组合并操作
			System.out.println("left:" + left + " 和 mid:" + mid + " 和 right:" + right + "已经归并完成！");
			System.out.println("==================");
		}
	}

	private static void merge(int[] arr, int left, int mid, int right, int[] temp) {
		int i = left;// 左序列指针
		int j = mid + 1;// 右序列指针
		int t = 0;// 临时数组指针
		while (i <= mid && j <= right) {
			if (arr[i] <= arr[j]) {
				temp[t++] = arr[i++];
			} else {
				temp[t++] = arr[j++];
			}
		}
		while (i <= mid) {// 将左边剩余元素填充进temp中
			temp[t++] = arr[i++];
		}
		while (j <= right) {// 将右序列剩余元素填充进temp中
			temp[t++] = arr[j++];
		}
		t = 0;
		// 将temp中的元素全部拷贝到原数组中
		while (left <= right) {
			arr[left++] = temp[t++];
		}
	}
}
