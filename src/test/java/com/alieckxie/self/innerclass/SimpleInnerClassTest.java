package com.alieckxie.self.innerclass;

import org.junit.Test;

import com.alieckxie.self.bean.BuilderSimpleBean;

public class SimpleInnerClassTest {

	@Test
	public void testInnerClass() {
		BuilderSimpleBean builderSimpleBean = new BuilderSimpleBean();
		BuilderSimpleBean simpleBean = builderSimpleBean.new Builder().setAge(1).setName("ASD").setGender("Male").build();
	}

}
