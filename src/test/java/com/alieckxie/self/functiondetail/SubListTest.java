package com.alieckxie.self.functiondetail;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.alieckxie.self.bean.TestBean;

public class SubListTest {

	@Test
	public void testSubList() throws IntrospectionException{
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("a");
		list.add("a");
		list.add("a");
		list.add("a");
		list.add("a");
		list.add("a");
		list.add("a");
		System.out.println(list.size());
		list = list.subList(0, 3);
		for (String string : list) {
			System.out.println(string);
		}
		System.out.println(list.size());
		PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(TestBean.class).getPropertyDescriptors();
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			System.out.println(propertyDescriptor.getPropertyType().getSimpleName());
			System.out.println(propertyDescriptor.getName());
		}
	}
	
}
