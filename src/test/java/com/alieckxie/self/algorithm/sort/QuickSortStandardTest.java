package com.alieckxie.self.algorithm.sort;

import java.util.Arrays;

import org.junit.Test;

import com.alieckxie.self.algorithm.util.MiscUtil;

public class QuickSortStandardTest {

	@Test
	public void testQuickSortStandard() {
		Integer[] array = MiscUtil.generateRandomIntegerArray(10, 100);
		System.out.println(Arrays.toString(array));
		quickSortStandard(array, 0, array.length - 1);
		System.out.println(Arrays.toString(array));
	}

	public <T extends Comparable<? super T>> void quickSortStandard(T[] targetArr, int left, int right) {
		System.out.println("start to process left:" + left + " to right:" + right + "*****************************");
		if (left < right) {
			int pivotPosition = partition(targetArr, left, right);
			System.out.println("-----partition is done!pivot position is:" + pivotPosition + "-------------------------------------------");
			System.out.println("-----sort pivotPosition:" + pivotPosition + " left part to left:" + left + "-----");
			quickSortStandard(targetArr, left, pivotPosition - 1);
			System.out.println("-----sort pivotPosition:" + pivotPosition + " right part to right:" + right + "----");
			quickSortStandard(targetArr, pivotPosition + 1, right);
			System.out.println("left:" + left + " to right:" + right + " partition is sorted done!\n");
		} else {
			System.out.println("left:" + left + " >= right:" + right + ", exit method!");
		}
	}

	private <T extends Comparable<? super T>> int partition(T[] targetArr, int left, int right) {
		T pivot = targetArr[left];
//		targetArr[left] = null;
		while (left < right) {
			System.out.println("\n#####New Loop!#####\n+++++++++right+++++++++");
			while (left < right && targetArr[right].compareTo(pivot) >= 0) {
				System.out.println("right:" + right + " value:" + targetArr[right] + " >= pivot:" + pivot);
				right--;
			}
			System.out.println("right:" + right + " value:" + targetArr[right] + " < pivot:" + pivot + "  ");
			String originArrStr = Arrays.toString(targetArr);
			System.out.println(originArrStr);
			printSwapArrow(originArrStr, left, right);
//			System.out.println("switch:left " + left + " value:" + targetArr[left] + " and right " + right + " value:" + targetArr[right]);
			System.out.println("Overwrite position:" + left + " value:" + targetArr[left] + " with position:" + right + " value:" + targetArr[right]);
			targetArr[left] = targetArr[right];
//			targetArr[right] = null;
			System.out.println(Arrays.toString(targetArr));
			
			
			System.out.println("++++++++++left+++++++++");
			while (left < right && targetArr[left].compareTo(pivot) <= 0) {
				System.out.println("left:" + left + " value:" + targetArr[left] + " <= pivot:" + pivot);
				left++;
			}
			System.out.println("left:" + left + " value:" + targetArr[left] + " > pivot:" + pivot + "  ");
			System.out.println(Arrays.toString(targetArr));
			printSwapArrow(originArrStr, left, right);
//			System.out.println("switch:right " + right + " value:" + targetArr[right] + " and left " + left + " value:" + targetArr[left]);
			System.out.println("Overwrite position:" + right + " value:" + targetArr[right] + " with position:" + left + " value:" + targetArr[left]);
			targetArr[right] = targetArr[left];
//			targetArr[left] = null;
			System.out.println(Arrays.toString(targetArr));
		}
		System.out.println("now left:" + left + ">= righ:" + right + ", set pivot:" + pivot + " back to place:" + left);
		targetArr[left] = pivot;
		return left;
	}

//	@Test
	public void testPrintSwapArrow() {
		String arrStr = "[0, 25, 6, 37, 37, 39, 39, 40, 55, 61]";
		System.out.println(arrStr);
		printSwapArrow(arrStr, 1, 6);
	}

	private void printSwapArrow(String originArrStr, int left, int right) {
		String[] split = originArrStr.split(",");
		StringBuilder arrStr = new StringBuilder(originArrStr.length());
		for (int i = 0; i < split.length; i++) {
			int length = split[i].length();
			if (i == left) { // 更换位箭头
				for (int j = 0; j < length; j++) {
					if (j < length/2) {
						arrStr.append(' ');
					} else if (j == length/2) {
						arrStr.append('↑');
					} else {
						arrStr.append('_');
					}
				}
				arrStr.append('_');
			} else if (i == right) {
				arrStr.append('_');
				for (int j = 0; j < length; j++) {
					if (j < length/2) {
						arrStr.append('_');
					} else if (j == length/2) {
						arrStr.append('↑');
					} else {
						break;
					}
				}
			} else if (i > left && i < right) { // 中间部分划横线
				for (int j = 0; j < length + 1; j++) { // 此处length + 1是为了填补逗号的位置
					arrStr.append('_');
				}
			} else { // 其他地方填空格
				if (i < right) {
					for (int j = 0; j < length + 1; j++) { // 此处length + 1是为了填补逗号的位置
						arrStr.append(' ');
					} 
				} else {
					break;
				}
			}
		}
		if (left == right) {
			arrStr.deleteCharAt(arrStr.length() - 1);
			arrStr.deleteCharAt(0);
			arrStr.append('↑');
		}
		System.out.println(arrStr);
	}
}
