package com.alieckxie.self.functiondetail;

import java.util.Map;
import java.util.WeakHashMap;

import org.junit.Test;

public class WeakHashMapTest {

	@Test
	public void testWeakHashMap() {
		int asd = (int) Math.pow(2D, 20D);
		System.out.println(asd);
		Map<String,Object> map = new WeakHashMap<String, Object>();
		for (int i = 0; i < 11; i++) {
			map.put(String.valueOf(i), new byte[asd]);
		}
//		map.put("0", new byte[asd]);
//		map.put("1", new byte[asd]);
//		map.put("2", new byte[asd]);
//		map.put("3", new byte[asd]);
//		map.put("4", new byte[asd]);
//		map.put("5", new byte[asd]);
//		map.put("6", new byte[asd]);
//		map.put("7", new byte[asd]);
//		map.put("8", new byte[asd]);
//		map.put("9", new byte[asd]);
		int count = 0;
		while (true) {
//			map.put(String.valueOf(count), new byte[asd]);
			System.out.println("size:" + map.size());
			System.out.println("map.contains(0):" + map.containsKey("0"));
			System.out.println("map.get(0):" + map.get("0"));
			System.out.println("map:" + map);
			System.out.println();
			count++;
			if (count % 100 == 50) {
				System.gc();
			}
			System.out.println("map.get(0):" + map.get("0"));
			System.out.println("map:" + map);
			System.out.println();
			System.out.println();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
	}
	
}
