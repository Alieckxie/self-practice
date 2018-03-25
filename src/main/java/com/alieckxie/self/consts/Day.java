package com.alieckxie.self.consts;

public enum Day {
	MONDAY("1"), TUESDAY("1"), WEDNESDAY("1"), THURSDAY("1"), FRIDAY("1"), SATURDAY("1"), SUNDAY("1");

	public final String desc;

	Day(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

}
