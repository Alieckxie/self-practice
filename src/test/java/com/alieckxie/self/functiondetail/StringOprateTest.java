package com.alieckxie.self.functiondetail;

import java.math.BigDecimal;
import java.util.Locale;

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
		String fgh = "1Y";
		System.out.println(fgh.replace("/", ""));
		System.out.println(fgh);
		System.out.println(fgh.substring(0, fgh.length() - 1));
	}

	@Test
	public void testStringAdd() {
		String str1 = "Asd";
		String str2 = " haha";
		String asd = str1 + str2;
		System.out.println(asd);
		System.out.println(Locale.CHINESE);
	}

	private void changeString(String a) {
		a = "fdasgfaas";
	}
	
	@Test
	public void testStringChange() {
		String str = "alieckxie";
		System.out.println(str);
		changeString(str);
		System.out.println(str);
	}
	
	@Test
	public void testStringCompare() {
		String dlCd1 = "300";
		String dlCd2 = "1000";
		System.out.println(dlCd1.compareToIgnoreCase(dlCd2));
	}
	
	@Test
	public void testUpperCase() {
		String asd = "天气不错";
		System.out.println(asd);
		System.out.println(asd.toUpperCase());
	}
	
	@Test
	public void testStringEquals() {
		String[] strings = new String[] {"AA", "AA", "AAA", "AAA"};
		System.out.println(strings[0] == strings[1]);
		System.out.println(strings[0] + "LOCK" == strings[1] + "LOCK");
	}
	
}
