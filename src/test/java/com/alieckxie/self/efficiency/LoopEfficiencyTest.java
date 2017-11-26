package com.alieckxie.self.efficiency;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
	
	private static final int rounds = 100000;
	private static final int size = 5000;
	
	public LinkedList<Map<String, Object>> getTestList() {
		LinkedList<Map<String,Object>> list = new LinkedList<Map<String, Object>>();
		for (int i = 0; i < size; i++) {
			HashMap<String,Object> map = new HashMap<String, Object>();
			list.add(map);
			map.put("prdct", "product");
			map.put("tm", new Date());
			map.put("ltst", new BigDecimal(Math.random() * 10 + 1));
		}
		return list;
	}
	
	@Test
	public void testLoopConcrete1() {
		LinkedList<Double> durings = new LinkedList<Double>();
		for (int i = 0; i < rounds; i++) {
			List<Map<String, Object>> list = getTestList();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			BigDecimal weight = new BigDecimal(100);
			long startTime = System.nanoTime();
			for (Map<String, Object> map : list) {
				map.put("prdct", (String) map.get("prdct") + Math.random());
				map.put("tm", format.format(map.get("tm")));
				map.put("ltst", ((BigDecimal) map.get("ltst")).multiply(weight));
			}
			Double during = (System.nanoTime() - startTime) / 1000000D;
			durings.add(during);
		}
		Double max = Collections.max(durings);
		Double min = Collections.min(durings);
		System.out.println("最大值：" + max);
		System.out.println("最小值：" + min);
		Collections.sort(durings);
		System.out.println("中位数1：" + durings.get(durings.size()/2));
		System.out.println("中位数2：" + durings.get(durings.size()/2 + 1));
		durings.remove(max);
		durings.remove(min);
		Double sum = 0D;
		for (Double double1 : durings) {
			sum += double1;
		}
		System.out.println("平均值：" + sum / durings.size());
		Double max1 = Collections.max(durings);
		Double min1 = Collections.min(durings);
		System.out.println("次大值：" + max1);
		System.out.println("次小值：" + min1);
	}
	
	@Test
	public void testLoopConcrete2() {
		LinkedList<Double> durings = new LinkedList<Double>();
		for (int i = 0; i < rounds; i++) {
			List<Map<String, Object>> list = getTestList();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			BigDecimal weight = new BigDecimal(100);
			long startTime = System.nanoTime();
			for (Map<String, Object> map : list) {
				map.put("prdct", (String) map.get("prdct") + Math.random());
			}
			for (Map<String, Object> map : list) {
				map.put("tm", format.format(map.get("tm")));
			}
			for (Map<String, Object> map : list) {
				map.put("ltst", ((BigDecimal) map.get("ltst")).multiply(weight));
			}
			Double during = (System.nanoTime() - startTime) / 1000000D;
			durings.add(during);
		}
		Collections.sort(durings);
		Double max = durings.get(durings.size() - 1);
		Double min = durings.get(0);
		System.out.println("最大值：" + max);
		System.out.println("最小值：" + min);
		System.out.println("中位数1：" + durings.get(durings.size()/2));
		System.out.println("中位数2：" + durings.get(durings.size()/2 + 1));
		durings.remove(durings.size() - 1);
		durings.remove(0);
		Double sum = 0D;
		for (Double double1 : durings) {
			sum += double1;
		}
		System.out.println("平均值：" + sum / durings.size());
		Double max1 = durings.get(durings.size() - 1);
		Double min1 = durings.get(0);
		System.out.println("次大值：" + max1);
		System.out.println("次小值：" + min1);
	}
	
	@Test
	public void testLoopConcrete3() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BigDecimal weight = new BigDecimal(100);
		for (int i = 0; i < 10; i++) {
			List<Map<String, Object>> list = getTestList();
			for (Map<String, Object> map : list) {
				map.put("prdct", (String) map.get("prdct") + Math.random());
				map.put("tm", format.format(map.get("tm")));
				map.put("ltst", ((BigDecimal) map.get("ltst")).multiply(weight));
			}
		}
		testLoopConcrete1();
		System.out.println("============================");
		testLoopConcrete2();
		System.out.println("============================");
		testLoopConcrete1();
		System.out.println("============================");
		testLoopConcrete2();
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		testLoopConcrete1();
		System.out.println("============================");
		testLoopConcrete2();
		System.out.println("============================");
		testLoopConcrete1();
		System.out.println("============================");
		testLoopConcrete2();
	}
}
