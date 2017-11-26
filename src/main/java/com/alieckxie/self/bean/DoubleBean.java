package com.alieckxie.self.bean;

public class DoubleBean {

	private double decimalA;
	private double decimalB;
	private double decimalC;

	public double getDecimalA() {
		return decimalA;
	}

	public void setDecimalA(double decimalA) {
		this.decimalA = decimalA;
	}

	public double getDecimalB() {
		return decimalB;
	}

	public void setDecimalB(double decimalB) {
		this.decimalB = decimalB;
	}

	public double getDecimalC() {
		return decimalC;
	}

	public void setDecimalC(double decimalC) {
		this.decimalC = decimalC;
	}

	@Override
	public String toString() {
		return "DoubleBean [decimalA=" + decimalA + ", decimalB=" + decimalB + ", decimalC=" + decimalC + "]";
	}

}
