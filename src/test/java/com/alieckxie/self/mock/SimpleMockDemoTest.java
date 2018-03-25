package com.alieckxie.self.mock;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

public class SimpleMockDemoTest {

	@Test
	public void test1() {

		// test for Mock
		List<String> mockList = Mockito.mock(List.class);
		mockList.add("1");
		System.out.println(mockList.get(0));// 返回null，说明并没有调用真正的方法
		Mockito.when(mockList.size()).thenReturn(100);// stub
		System.out.println(mockList.size());// size() method was stubbed，返回100
		Mockito.when(mockList.get(0)).thenReturn("100");// stub
		System.err.println(mockList.get(0));// 返回100，说明mock成功了

		// test for Spy
		List list = new LinkedList();
		List<String> spy = Mockito.spy(list);

		// optionally, you can stub out some methods:
		Mockito.when(spy.size()).thenReturn(100);

		// using the spy calls real methods
		spy.add("one");
		spy.add("two");

		// prints "one" - the first element of a list
		System.out.println(spy.get(0));

		// size() method was stubbed - 100 is printed
		System.out.println(spy.size());
	}
}
