package com.alieckxie.self.algorithm.util;

import java.util.Random;

public class MiscUtil {

	public static int[] generateRandomArray() {
		Random random = new Random();
		int length = 10;
		int[] array = new int[length];
		for (int i = 0; i < length; i++) {
			array[i] = random.nextInt(100);
		}
		return array;
	}

	public static Integer[] generateRandomIntegerArray(int length, int range) {
		Integer[] array = generateRandomIntegerArray(length, range, 0);
		return array;
	}

	public static Integer[] generateRandomIntegerArray(int length, int range, int lowerLimit) {
		Random random = new Random();
		Integer[] array = new Integer[length];
		for (int i = 0; i < length; i++) {
			array[i] = lowerLimit + random.nextInt(range);
		}
		return array;
	}

	public static void printSwapArrow(String originArrStr, int left, int right) {
		String[] split = originArrStr.split(",");
		StringBuilder arrStr = new StringBuilder(originArrStr.length());
		for (int i = 0; i < split.length; i++) {
			int length = split[i].length();
			if (i == left) { // 更换位箭头
				for (int j = 0; j < length; j++) {
					if (j < length / 2) {
						arrStr.append(' ');
					} else if (j == length / 2) {
						arrStr.append('↑');
					} else {
						arrStr.append('_');
					}
				}
				arrStr.append('_');
			} else if (i == right) {
				arrStr.append('_');
				for (int j = 0; j < length; j++) {
					if (j < length / 2) {
						arrStr.append('_');
					} else if (j == length / 2) {
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
