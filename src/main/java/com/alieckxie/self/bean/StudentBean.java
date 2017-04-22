package com.alieckxie.self.bean;

import java.util.List;

public class StudentBean {

	private String name;

	private Integer age;

	private List<String> courses;

	private List<TeacherBean> teachers;
	
	private String comment;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = this.comment;
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public List<String> getCourses() {
		return courses;
	}

	public void setCourses(List<String> courses) {
		this.courses = courses;
	}

	public List<TeacherBean> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<TeacherBean> teachers) {
		this.teachers = teachers;
	}

	@Override
	public String toString() {
		return "StudentBean [name=" + name + ", age=" + age + ", courses=" + courses + ", teachers=" + teachers + "]";
	}

}
