package com.alieckxie.self.bean;

public class StudentCamelBeanDiffPropType {

	private String sName;

	private int sAge;

	private String sComment;

	public StudentCamelBeanDiffPropType() {
		super();
	}

	public StudentCamelBeanDiffPropType(String sName, Integer sAge, String sComment) {
		super();
		this.sName = sName;
		this.sAge = sAge;
		this.sComment = sComment;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
		System.out.println("--------调用了StudentCamelBeanDiffPropType的sName的setter（sName）--------");
	}

	public int getsAge() {
		return sAge;
	}

	public void setsAge(int sAge) {
		this.sAge = sAge;
		System.out.println("--------调用了StudentCamelBeanDiffPropType的sAge的setter（sAge）--------");
	}

	public String getsComment() {
		return sComment;
	}

	public void setsComment(String sComment) {
		this.sComment = sComment;
		System.out.println("--------调用了StudentCamelBeanDiffPropType的sComment的setter（sComment）--------");
	}

	@Override
	public String toString() {
		return "StudentCamelBeanDiffPropType [sName=" + sName + ", sAge=" + sAge + ", sComment=" + sComment + "]";
	}

}
