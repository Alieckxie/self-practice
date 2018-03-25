package com.alieckxie.self.functiondetail;

import java.util.Random;

import org.junit.Test;

public class RandomTest {

	@Test
	public void testSimpleRandom() {
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			System.out.println(random.nextInt(2));
		}
	}
}
