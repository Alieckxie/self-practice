package com.alieckxie.self.consts;

public class CommonConsts {
	public enum TradingMethod {
		QDM, ODM;
	}
	
	public void testCompile() {
		if (Day.MONDAY.desc.equals("ASD")) {
			System.out.println("HAHAHA");
		}
		Day[] values = Day.values();
		System.out.println(values.length);
	}
}
