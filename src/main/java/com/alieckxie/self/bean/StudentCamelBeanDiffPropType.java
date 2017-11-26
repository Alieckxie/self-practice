package com.alieckxie.self.bean;

import java.math.BigDecimal;

public class StudentCamelBeanDiffPropType {

	private String sName;

	private BigDecimal sAge;

	private String sComment;

	public StudentCamelBeanDiffPropType() {
		super();
	}

	public StudentCamelBeanDiffPropType(String sName, BigDecimal sAge, String sComment) {
		super();
		this.sName = sName;
		this.sAge = sAge;
		this.sComment = sComment;
	}

	public String getsName() {
		System.out.println("--------调用了StudentCamelBeanDiffPropType的sName的getter（sName）--------");
		return sName;
	}

	public void setsName(String sName) {
		System.out.println("--------调用了StudentCamelBeanDiffPropType的sName的setter（sName）--------");
		this.sName = sName;
	}

//	public BigDecimal getsAge() {
//		System.out.println("--------调用了StudentCamelBeanDiffPropType的sAge的getter（sAge）--------");
//		return sAge;
//	}

	public void setsAge(BigDecimal sAge) {
		System.out.println("--------调用了StudentCamelBeanDiffPropType的sAge的setter（sAge）--------");
		this.sAge = sAge;
	}
	
	public void setsAge(Integer sAge) {
		System.out.println("--------调用了StudentCamelBeanDiffPropType的sAge的setter（sAge）Integer--------");
		this.sAge = new BigDecimal(sAge);
	}

	public String getsComment() {
		System.out.println("--------调用了StudentCamelBeanDiffPropType的sComment的getter（sComment）--------");
		return sComment;
	}

	public void setsComment(String sComment) {
		System.out.println("--------调用了StudentCamelBeanDiffPropType的sComment的setter（sComment）--------");
		this.sComment = sComment;
	}

	@Override
	public String toString() {
		return "StudentCamelBeanDiffPropType [sName=" + sName + ", sAge=" + sAge + ", sComment=" + sComment + "]";
	}

}
