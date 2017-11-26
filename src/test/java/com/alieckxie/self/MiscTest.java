package com.alieckxie.self;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class MiscTest {

	@Test
	public void testTimestamp() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		System.out.println(timestamp);
	}

	@Test
	public void testPrintList() {
		List<String> list = new ArrayList<String>();
		list.add("asd");
		list.add("asd");
		list.add("asd");
		System.out.println(list);
	}

	@Test
	public void testStringByteLength() {
		String a = "天气不错！a";
		byte[] bytes = a.getBytes();
		System.out.println(a.length());
		System.out.println(bytes.length);
	}

	@Test
	public void testBigDecimalToPlainString() {
		BigDecimal bigDecimal = BigDecimal.valueOf(3.2100);
		BigDecimal divide = bigDecimal.divide(new BigDecimal(1), 4, RoundingMode.HALF_UP);
		System.out.println(divide.toPlainString());
	}

}
