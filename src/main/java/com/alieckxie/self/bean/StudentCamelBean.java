package com.alieckxie.self.bean;

public class StudentCamelBean {

	private String sName;

	private Integer sAge;

	private String sComment;

	public StudentCamelBean() {
		super();
	}

	public StudentCamelBean(String sName, Integer sAge, String sComment) {
		super();
		this.sName = sName;
		this.sAge = sAge;
		this.sComment = sComment;
	}

	public String getsName() {
		System.out.println("--------调用了StudentCamelBean的sName的getter（sName）--------");
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public Integer getsAge() {
		System.out.println("--------调用了StudentCamelBean的sAge的getter（sAge）--------");
		return sAge;
	}

	public void setsAge(Integer sAge) {
		this.sAge = sAge;
	}

	public String getsComment() {
		System.out.println("--------调用了StudentCamelBean的sComment的getter（sComment）--------");
		return sComment;
	}

	public void setsComment(String sComment) {
		this.sComment = sComment;
	}

	@Override
	public String toString() {
		return "StudentCamelBean [sName=" + sName + ", sAge=" + sAge + ", sComment=" + sComment + "]";
	}

}
