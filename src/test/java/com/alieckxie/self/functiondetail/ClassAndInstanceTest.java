package com.alieckxie.self.functiondetail;

import org.junit.Test;

public class ClassAndInstanceTest {

	@Test
	public void testInstanceOf() {
		Object a = null;
		if (a instanceof String) {
			System.out.println("this is String!");
		} else {
			System.out.println("this is not String...");
		}
	}
	
}
