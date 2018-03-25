package com.alieckxie.self.bean;

public class BuilderSimpleBean {

	private String name;

	private int age;

	private String gender;

	public BuilderSimpleBean() {
		super();
	}

	public class Builder {
		private String name;

		private int age;

		private String gender;

		public Builder setName(String name) {
			this.name = name;
			return this;
		}

		public Builder setAge(int age) {
			this.age = age;
			return this;
		}

		public Builder setGender(String gender) {
			this.gender = gender;
			return this;
		}

		public BuilderSimpleBean build() {
			return new BuilderSimpleBean(this);
		}

	}

	private BuilderSimpleBean(Builder build) {
		this.name = build.name;
		this.age = build.age;
		this.gender = build.gender;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public String getGender() {
		return gender;
	}

}
