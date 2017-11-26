package com.alieckxie.self.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import org.junit.Test;

import com.alieckxie.self.bean.StudentBean;

public class ReflectTest {

	@Test
	public void testReflectField() throws NoSuchFieldException, SecurityException {
		Class<?> clazz = StudentBean.class;
		Field field = clazz.getDeclaredField("name");
		Type genericType = field.getGenericType();
	}
	
}
