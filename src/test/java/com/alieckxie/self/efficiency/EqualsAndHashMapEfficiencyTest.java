package com.alieckxie.self.efficiency;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class EqualsAndHashMapEfficiencyTest {

	private static int counts = 100000;
	private static final double nanoDivide = 1000000D;

	@Test
	public void testEqualsEfficiency() {
		String a = "fdafgads";
		String b = "fdafgads";
		// String b = "dasgfa";
		String c = "dsgfds";
		String d = "hjgfjhf";
		String e = "ytri67uuyr";
		long start = System.nanoTime();
		for (int i = 0; i < counts; i++) {
			if (a.equals(b)) {

			} else if (b.equals(a)) {

			} else if (e.equals(d)) {

			} else if (d.equals(a)) {

			} else {

			}
		}
		System.out.println("testEqualsEfficiency:" + (System.nanoTime() - start) / nanoDivide);
	}

	@Test
	public void testHashMapEfficiency() {
		String a = "fdafgads";
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("fdasgafg", 0);
		map.put("gfsdgfs", 1);
		map.put("hfdergfds", 2);
		map.put("gfsdgfs", 3);
		map.put("fdasvczvdsf", 4);
		map.put("ayterhgd", 5);
		map.put("bvcxnbxbx", 6);
		map.put("q4tre2trw@43154$", 7);
		map.put("avczvcz", 8);
		map.put("kjghipyio", 9);
		long start = System.nanoTime();
		for (int i = 0; i < counts; i++) {
			Integer integer = map.get(a);
		}
		System.out.println("testHashMapEfficiency:" + (System.nanoTime() - start) / nanoDivide);
	}

	@Test
	public void testSwitchEfficiency() {
		String a = "gfauion23nfdnas";
		long start = System.nanoTime();
		for (int i = 0; i < counts; i++) {
			Integer integer = getMatchedInteger(a);
		}
		System.out.println("testSwitchEfficiency:" + (System.nanoTime() - start) / nanoDivide);
	}

	private Integer getMatchedInteger(String key) {
		Integer a;
		switch (key) {
		case "gfsthgs":
			a = 0;
			break;
		case "fdasgfa":
			a = 1;
			break;
		case "gfsgf":
			a = 2;
			break;
		case "sa2rq45tgrsgfs":
			a = 3;
			break;
		case "jgfglktiu":
			a = 4;
			break;
		case "bvxfgssfd":
			a = 5;
			break;
		case "ghnbcnb":
			a = 6;
			break;
		case "645jhfgncbg":
			a = 7;
			break;
		case "432 yu sgfjszvch":
			a = 8;
			break;
		default:
			a = 9;
			break;
		}
		return a;
	}

}
