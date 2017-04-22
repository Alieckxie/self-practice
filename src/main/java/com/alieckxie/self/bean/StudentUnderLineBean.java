package com.alieckxie.self.bean;

public class StudentUnderLineBean {

	private String s_name;

	private Integer s_age;

	private String s_comment;

	public StudentUnderLineBean() {
		super();
	}

	public StudentUnderLineBean(String s_name, Integer s_age, String s_comment) {
		super();
		this.s_name = s_name;
		this.s_age = s_age;
		this.s_comment = s_comment;
	}

	public String getS_name() {
		System.out.println("--------调用了StudentUnderLineBean的s_name的getter（s_name）--------");
		return s_name;
	}

	public void setS_name(String s_name) {
		this.s_name = s_name;
		System.out.println("--------调用了StudentUnderLineBean的s_name的setter（s_name）--------");
	}

	public Integer getS_age() {
		System.out.println("--------调用了StudentUnderLineBean的s_age的getter（s_age）--------");
		return s_age;
	}

	public void setS_age(Integer s_age) {
		System.out.println("--------调用了StudentUnderLineBean的s_age的setter（s_age）--------");
		this.s_age = s_age;
	}

	public String getS_comment() {
		System.out.println("--------调用了StudentUnderLineBean的s_comment的getter（s_comment）--------");
		return s_comment;
	}

	public void setS_comment(String s_comment) {
		System.out.println("--------调用了StudentUnderLineBean的s_comment的setter（s_comment）--------");
		this.s_comment = s_comment;
	}
	
	public String getsName() {
		System.out.println("--------调用了StudentUnderLineBean的s_name的getter（s_name）--------");
		return s_name;
	}
	
	public void setsName(String s_name) {
		this.s_name = s_name;
		System.out.println("--------调用了StudentUnderLineBean的s_name的setter（s_name）--------");
	}
	
	public Integer getsAge() {
		System.out.println("--------调用了StudentUnderLineBean的s_age的getter（s_age）--------");
		return s_age;
	}
	
	public void setsAge(Integer s_age) {
		System.out.println("--------调用了StudentUnderLineBean的s_age的setter（s_age）--------");
		this.s_age = s_age;
	}
	
	public String getsComment() {
		System.out.println("--------调用了StudentUnderLineBean的s_comment的getter（s_comment）--------");
		return s_comment;
	}
	
	public void setsComment(String s_comment) {
		System.out.println("--------调用了StudentUnderLineBean的s_comment的setter（s_comment）--------");
		this.s_comment = s_comment;
	}

	@Override
	public String toString() {
		return "Student_Bean [s_name=" + s_name + ", s_age=" + s_age + ", s_comment=" + s_comment + "]";
	}

}
