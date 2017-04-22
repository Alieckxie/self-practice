package com.alieckxie.self;

import org.junit.Test;

public class TryCatchFinallyReturnTest {

	@Test
	public void testTCFR(){
		int return1 = getReturn(110);
		System.out.println(return1);
	}
	
	public int getReturn(int a){
		try {
			throw new Exception();
//			return a++;
		} catch (Exception e) {
			// TODO: handle exception
			return a--;
		} finally {
			
		}
//		System.out.println();
	}
	
}
