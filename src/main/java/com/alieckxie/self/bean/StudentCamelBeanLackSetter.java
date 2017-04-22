package com.alieckxie.self.bean;

public class StudentCamelBeanLackSetter {

	private String sName;

	private Integer sAge;

	private String sComment;

	public StudentCamelBeanLackSetter() {
		super();
	}

	public StudentCamelBeanLackSetter(String sName, Integer sAge, String sComment) {
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
	}

	public Integer getsAge() {
		return sAge;
	}

	public void setsAge(Integer sAge) {
		this.sAge = sAge;
	}

	public String getsComment() {
		return sComment;
	}

//	public void setsComment(String sComment) {
//		this.sComment = sComment;
//	}

	@Override
	public String toString() {
		return "StudentCamelBeanLackSetter [sName=" + sName + ", sAge=" + sAge + ", sComment=" + sComment + "]";
	}

}
