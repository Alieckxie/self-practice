package com.alieckxie.self.algorithm;

import java.util.Arrays;

import org.junit.Test;

public class KmpMatchTest {

	@Test
	public void testKmp1() {
		String str = "abaabcac";
		char[] t = str.toCharArray();
		int i = 0;
		int[] next = new int[t.length];
		next[0] = -1;
		int j = -1;
		while (i < t.length - 1) {
			if (j == -1 || t[i] == t[j]) {
				++i;
				++j;
				next[i] = j;
			} else {
				j = next[j];
			}
		}
		System.out.println(Arrays.toString(t));
		System.out.println(Arrays.toString(next));
	}

	@Test
	public void testKMPMatch() {
		String mainStr = "abafdkaabaahjflabaabfdsuqabaabcac";
		String patter = "abaabcac";
		int matchKMP = matchKMP(mainStr, patter);
		System.out.println(matchKMP);
	}

	public static int[] getNext(String ps) {
		char[] p = ps.toCharArray();
		int[] next = new int[p.length];
		next[0] = -1;
		System.out.println("--->next[(j)0] = -1 已初始化");
		int j = 0;
		int k = -1;
		System.out.println(Arrays.toString(p));
		while (j < p.length - 1) {
			System.out.println("==================");
			System.out.println("k = " + k + "; p[(j)" + j + "]: " + p[j]);
			if (k == -1 || p[j] == p[k]) {
				if (k == -1) {
					System.out.println("k == -1");
				} else {
					System.out.println("p[(j)" + j + "]: " + p[j] + "== p[(k)" + k + "]: " + p[k]);
				}
				++j;
				++k;
				System.out.println("--->next[(j)" + j + "] = k: " + k);
				next[j] = k;
				// 以下是改进的next数组
				// if (p[j] == p[k]) {
				// next[j] = next[k];
				// } else {
				// next[j] = k;
				// }
			} else {
				System.out.println("p[(j)" + j + "] " + p[j] + " !!!!==== p[(k)" + k + "] " + p[k]);
				System.out.println("重置 k: " + k + " = next[(k)" + k + "]: " + next[k]);
				k = next[k];
			}
		}
		System.out.println(Arrays.toString(next));
		return next;
	}

	public static int matchKMP(String ts, String ps) {
		char[] t = ts.toCharArray();
		char[] p = ps.toCharArray();
		int i = 0; // 主串的位置
		int j = 0; // 模式串的位置
		int[] next = getNext(ps);
		// printMatchSituation(ts, ps, i, j);
		while (i < t.length && j < p.length) {
			if (j == -1 || t[i] == p[j]) { // 当j为-1时，要移动的是i，当然j也要归0
				if (j != -1) {
					System.out.println("此处匹配，情况如下：");
					printMatchSituation(ts, ps, i, j);
				} else {
					System.out.println("j == -1，在下一位匹配，两个指针后移后进入下一次循环");
					System.out.println("==================");
				}
				i++;
				j++;
			} else {
				// i不需要回溯了
				// i = i - j + 1;
				printMatchSituation(ts, ps, i, j);
				j = next[j]; // j回到指定位置
				System.out.println("未能匹配，下一次从" + j + "处开始进行匹配");
				System.out.println("==================");
			}
		}
		if (j == p.length) {
			return i - j;
		} else {
			return -1;
		}
	}

	private static void printMatchSituation(String ts, String ps, int i, int j) {
		StringBuilder sb = new StringBuilder();
		// 主串指针需要i个pad
		for (int k = 0; k < i; k++) {
			sb.append(' ');
		}
		String startPoint = sb.toString();
		System.out.println(startPoint + "↓");
		System.out.println(ts);
		// pattern的指针和主串的指针是要对齐的，于是pattern只需要i-j个pad
		for (int k = -1; k < j - 1; k++) {
			sb.deleteCharAt(sb.length() - 1);
		}
		System.out.println(sb + ps);
		System.out.println(startPoint + "↑");
		System.out.println("******************");
	}
}
