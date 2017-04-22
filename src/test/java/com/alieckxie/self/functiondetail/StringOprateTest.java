package com.alieckxie.self.functiondetail;

import java.math.BigDecimal;

import org.junit.Test;

public class StringOprateTest {

	@Test
	public void testSplitString() {
		String a = "A:asd.fgh";
		a = a.substring(2);
		System.out.println(a);
		String[] strings = a.split("\\.");
		for (String string : strings) {
			System.out.println(string);
		}
	}

	@Test
	public void testSubString() {
		String asd = "USD/CNY";
		System.out.println(asd.length());
		if (asd.length() > 0) {
			System.out.println(asd.substring(0, 3));
			System.out.println(asd.length());
		}
		double as = 3.8700E14;
		System.out.println(as);
		BigDecimal valueOf = BigDecimal.valueOf(as);
		BigDecimal bigDecimal = new BigDecimal(as);
		System.out.println(bigDecimal);
		System.out.println(valueOf);
	}

	@Test
	public void testEndWith() {
		String asd = "asd.";
		System.out.println(asd.endsWith("."));
		System.out.println(asd);
		System.out.println(asd.substring(0, asd.length() - 1));
	}
	
	@Test
	public void testReplace() {
		String asd = "asd.fdas/fff2017-03-07";
		System.out.println(asd.replace("\\.", " vs "));
		System.out.println(asd.replace("-", ""));
		System.out.println(asd);
		System.out.println(asd.substring(0, asd.length() - 1));
	}

}
