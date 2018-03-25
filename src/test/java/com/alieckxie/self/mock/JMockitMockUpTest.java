package com.alieckxie.self.mock;

import org.junit.Assert;
import org.junit.Test;

import mockit.Mock;
import mockit.MockUp;

public class JMockitMockUpTest {
	public static class AnOrdinaryClassMockUp extends MockUp<AnOrdinaryClass> {
		// Mock静态方法
		@Mock
		public static int staticMethod() {
			return 10;
		}

		// Mock普通方法
		@Mock
		public int ordinaryMethod() {
			return 20;
		}

		@Mock
		// Mock final方法
		public final int finalMethod() {
			return 30;
		}

		// Mock native方法
		@Mock
		public int navtiveMethod() {
			return 40;
		}

		// Mock private方法
		@Mock
		private int privateMethod() {
			return 50;
		}
	};

	@Test
	public void testClassMockingByMockUp() {
		new AnOrdinaryClassMockUp();
		AnOrdinaryClass instance = new AnOrdinaryClass();
		// 静态方法被mock了
		Assert.assertTrue(AnOrdinaryClass.staticMethod() == 10);
		// 普通方法被mock了
		Assert.assertTrue(instance.ordinaryMethod() == 20);
		// final方法被mock了
		Assert.assertTrue(instance.finalMethod() == 30);
		// native方法被mock了
		Assert.assertTrue(instance.navtiveMethod() == 40);
		// private方法被mock了
		Assert.assertTrue(instance.callPrivateMethod() == 50);
	}

	private static class AnOrdinaryClass {

		// 静态方法
		public static int staticMethod() {
			return 1;
		}

		// 普通方法
		public int ordinaryMethod() {
			return 2;
		}

		// final方法
		public final int finalMethod() {
			return 3;
		}

		// native方法,返回4
		public native int navtiveMethod();

		// private方法
		private int privateMethod() {
			return 5;
		}

		// 调用private方法
		public int callPrivateMethod() {
			return privateMethod();
		}
	}
}
