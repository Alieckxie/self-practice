package com.alieckxie.self.efficiency;

import org.junit.Test;

public class LoopEfficiencyTest {

	/**
	 * 测试循环效率的第一组
	 */
	@Test
	public void testLoop1() {
		String A = "";
		String B = "";
		String C = "";
		long start = System.currentTimeMillis();
		for (int i = 0; i < 90000; i++) {
			A = A + "A";
			B = B + "B";
			C = C + "C";
		}
		System.out.println(System.currentTimeMillis() - start);
		System.out.println("Loop1中，A的长度为：" + A.length() + ",B的长度为：" + B.length() + ",C的长度为：" + C.length());
	}

	/**
	 * 测试循环效率的第二组
	 */
	@Test
	public void testLoop2() {
		String A = "";
		String B = "";
		String C = "";
		long start = System.currentTimeMillis();
		for (int i = 0; i < 90000; i++) {
			A = A + "A";
		}
		for (int i = 0; i < 90000; i++) {
			B = B + "B";
		}
		for (int i = 0; i < 90000; i++) {
			C = C + "C";
		}
		System.out.println(System.currentTimeMillis() - start);
		System.out.println("Loop2中，A的长度为：" + A.length() + ",B的长度为：" + B.length() + ",C的长度为：" + C.length());
	}

	/**
	 * 测试循环效率的第三组
	 */
	@Test
	public void testLoop3() {
		String A = "";
		String B = "";
		String C = "";
		long start = System.currentTimeMillis();
		for (int i = 0; i < 90000; i++) {
			A += "A";
			B += "B";
			C += "C";
		}
		System.out.println(System.currentTimeMillis() - start);
		System.out.println("Loop3中，A的长度为：" + A.length() + ",B的长度为：" + B.length() + ",C的长度为：" + C.length());
	}

	/**
	 * 测试循环效率的第四组
	 */
	@Test
	public void testLoop4() {
		String A = "";
		String B = "";
		String C = "";
		long start = System.currentTimeMillis();
		for (int i = 0; i < 90000; i++) {
			A += "A";
		}
		for (int i = 0; i < 90000; i++) {
			B += "B";
		}
		for (int i = 0; i < 90000; i++) {
			C += "C";
		}
		System.out.println(System.currentTimeMillis() - start);
		System.out.println("Loop4中，A的长度为：" + A.length() + ",B的长度为：" + B.length() + ",C的长度为：" + C.length());
	}
}
