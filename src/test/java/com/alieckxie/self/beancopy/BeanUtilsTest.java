package com.alieckxie.self.beancopy;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.core.ReflectUtils;

import com.alieckxie.self.bean.StudentBean;
import com.alieckxie.self.bean.StudentCamelBean;
import com.alieckxie.self.bean.StudentCamelBeanDiffPropType;
import com.alieckxie.self.bean.TeacherBean;
import com.alieckxie.self.util.SelfBeanUtils;

public class BeanUtilsTest {
	
	@Test
	public void testReflectUtilGetGetter() {
		PropertyDescriptor[] getters = ReflectUtils.getBeanGetters(StudentCamelBeanDiffPropType.class);
		for (PropertyDescriptor propertyDescriptor : getters) {
			System.out.println("getPropertyType:" + propertyDescriptor.getPropertyType());
			System.out.println("getName:" + propertyDescriptor.getName());
			System.out.println("getDisplayName:" + propertyDescriptor.getDisplayName());
			System.out.println("getReadMethod:" + propertyDescriptor.getReadMethod());
			System.out.println("getWriteMethod:" + propertyDescriptor.getWriteMethod());
			System.out.println("====================================");
		}
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		PropertyDescriptor[] setters = ReflectUtils.getBeanSetters(StudentCamelBeanDiffPropType.class);
		for (PropertyDescriptor propertyDescriptor : setters) {
			System.out.println("getPropertyType:" + propertyDescriptor.getPropertyType());
			System.out.println("getName:" + propertyDescriptor.getName());
			System.out.println("getDisplayName:" + propertyDescriptor.getDisplayName());
			System.out.println("getReadMethod:" + propertyDescriptor.getReadMethod());
			System.out.println("getWriteMethod:" + propertyDescriptor.getWriteMethod());
			System.out.println("====================================");
		}
	}
	
	@Test
	public void testIntrospector() throws IntrospectionException {
		BeanInfo beanInfo = Introspector.getBeanInfo(StudentCamelBeanDiffPropType.class);
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			System.out.println("getPropertyType:" + propertyDescriptor.getPropertyType());
			System.out.println("getName:" + propertyDescriptor.getName());
			System.out.println("getDisplayName:" + propertyDescriptor.getDisplayName());
			System.out.println("getReadMethod:" + propertyDescriptor.getReadMethod());
			System.out.println("getWriteMethod:" + propertyDescriptor.getWriteMethod());
			System.out.println("====================================");
		}
		// Introspector是有缓存的，所以可以一直使用
		BeanInfo beanInfo2 = Introspector.getBeanInfo(StudentCamelBeanDiffPropType.class);
	}
	
	@Test
	public void testMap2Bean() throws IllegalAccessException, InvocationTargetException, IntrospectionException {
		StudentBean student = new StudentBean();
		student.setAge(23);
		student.setName("Alieckxie");
		List<String> courses = new ArrayList<String>();
		courses.add("Language");
		courses.add("Math");
		ArrayList<TeacherBean> teachers = new ArrayList<TeacherBean>();
		TeacherBean teacher1 = new TeacherBean();
		teacher1.setAge(45);
		teacher1.setName("HAHAHA");
		teacher1.setCourses(courses);
		student.setCourses(courses);
		List<StudentBean> students = new ArrayList<StudentBean>();
		student.setTeachers(teachers);
		teacher1.setStudents(students);
		teachers.add(teacher1);
		student.setTeachers(teachers);
		Map<String, Object> convertBean = SelfBeanUtils.convertBean(student);
		Set<Map.Entry<String, Object>> entrySet = convertBean.entrySet();
		for (Map.Entry<String, Object> entry : entrySet) {
			System.out.println(entry.getKey() + "-->" + entry.getValue());
		}
	}

	@Test
	public void testBean2UnderLineMap()
			throws IllegalAccessException, InvocationTargetException, IntrospectionException {
		StudentCamelBean studentCamelBean = new StudentCamelBean("Alieckxie", 25, "天气还是很不错！");
		long start = System.currentTimeMillis();
		Map<String, Object> convertBean = new HashMap<String, Object>();
		for (int i = 0; i < 1000; i++) {
			convertBean = SelfBeanUtils.convertBeanToUnderLineMap(studentCamelBean);
		}
		System.out.println("拷贝用时：" + (System.currentTimeMillis() - start));
		Set<Map.Entry<String, Object>> entrySet = convertBean.entrySet();
		for (Map.Entry<String, Object> entry : entrySet) {
			System.out.println(entry.getKey() + "-->" + entry.getValue());
		}
	}
	
	@Test
	public void testSpringBeanUtils()
			throws IllegalAccessException, InvocationTargetException, IntrospectionException {
		StudentCamelBean studentCamelBean = new StudentCamelBean("Alieckxie", 25, "天气还是很不错！");
		Map<String, Object> convertBean = new HashMap<String, Object>();
		StudentCamelBean studentCamelBean2 = new StudentCamelBean();
		System.out.println(studentCamelBean2);
		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			BeanUtils.copyProperties(studentCamelBean, studentCamelBean2);
		}
		System.out.println("拷贝用时：" + (System.currentTimeMillis() - start));
		System.out.println(studentCamelBean2);
		Set<Map.Entry<String, Object>> entrySet = convertBean.entrySet();
		for (Map.Entry<String, Object> entry : entrySet) {
			System.out.println(entry.getKey() + "-->" + entry.getValue());
		}
	}

}
