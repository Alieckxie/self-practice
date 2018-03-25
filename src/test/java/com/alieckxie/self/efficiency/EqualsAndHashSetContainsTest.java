package com.alieckxie.self.efficiency;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class EqualsAndHashSetContainsTest {
	
	public static final String ON = "O/N";
	public static final String TN = "T/N";
	public static final String SN = "S/N";
	public static final Set<String> PRDS = new HashSet<String>();
	
	static {
		PRDS.add(ON);
		PRDS.add(TN);
		PRDS.add(SN);
		PRDS.add("1W");
		PRDS.add("1D");
		PRDS.add("3W");
		PRDS.add("1M");
	}

	@Test
	public void testMain() {
		Set<String> set = new HashSet<String>();
		set.add(ON);
		set.add(TN);
		set.add(SN);
		set.add("1W");
		set.add("1D");
		set.add("3W");
		set.add("1M");
		for (int i = 0; i < 1000000; i++) {
			for (String prd : set) {
				ifTest(prd);
			} 
		}
		for (int i = 0; i < 1000000; i++) {
			for (String prd : set) {
				containsTest(prd);
			} 
		}
		long currentTimeMillis = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			for (String prd : set) {
				ifTest(prd);
			} 
		}
		System.out.println(System.currentTimeMillis() - currentTimeMillis);
		currentTimeMillis = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			for (String prd : set) {
				containsTest(prd);
			} 
		}
		System.out.println(System.currentTimeMillis() - currentTimeMillis);
	}
	
	private static boolean ifTest(String prd) {
		if (ON.equals(prd) || TN.equals(prd) || SN.equals(prd)) {
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean containsTest(String prd) {
		if (PRDS.contains(prd)) {
			return true;
		} else {
			return false;
		}
	}
	
}
