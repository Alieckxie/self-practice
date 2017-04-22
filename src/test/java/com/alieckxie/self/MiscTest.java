package com.alieckxie.self;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class MiscTest {
	
	@Test
	public void testTimestamp(){
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		System.out.println(timestamp);
	}
	
	@Test
	public void testPrintList(){
		List<String> list = new ArrayList<String>();
		list.add("asd");
		list.add("asd");
		list.add("asd");
		System.out.println(list);
	}
	
}
