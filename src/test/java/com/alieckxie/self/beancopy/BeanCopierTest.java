package com.alieckxie.self.beancopy;

import org.junit.Test;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

import com.alieckxie.self.bean.StudentCamelBean;
import com.alieckxie.self.bean.StudentCamelBeanDiffPropType;
import com.alieckxie.self.bean.StudentCamelBeanLackSetter;
import com.alieckxie.self.bean.StudentUnderLineBean;

@SuppressWarnings("rawtypes")
public class BeanCopierTest {

	@Test
	public void testBeanCopierLackSetter() {
		BeanCopier copy = BeanCopier.create(StudentCamelBean.class, StudentCamelBeanLackSetter.class, false);
		StudentCamelBean camelBean = new StudentCamelBean("alieckxie", 25, "天气不错");
		StudentCamelBeanLackSetter camelBean2 = new StudentCamelBeanLackSetter();
		System.out.println(camelBean2);
		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			copy.copy(camelBean, camelBean2, null);
		}
		System.out.println("拷贝用时：" + (System.currentTimeMillis() - start));
		System.out.println(camelBean2);
	}

	@Test
	public void testBeanCopierDiffPropType() {
		BeanCopier copy = BeanCopier.create(StudentCamelBean.class, StudentCamelBeanDiffPropType.class, false);
		StudentCamelBean camelBean = new StudentCamelBean("alieckxie", 25, "天气不错");
		StudentCamelBeanDiffPropType camelBean2 = new StudentCamelBeanDiffPropType();
		System.out.println(camelBean2);
		copy.copy(camelBean, camelBean2, null);
		System.out.println(camelBean2);
	}

	@Test
	public void testBeanCopierConverterType() {
		BeanCopier copy = BeanCopier.create(StudentCamelBean.class, StudentCamelBeanDiffPropType.class, true);
		StudentCamelBean camelBean = new StudentCamelBean("alieckxie", 25, "天气不错");
		StudentCamelBeanDiffPropType camelBean2 = new StudentCamelBeanDiffPropType();
		System.out.println(camelBean2);
		System.out.println("*********************");
		copy.copy(camelBean, camelBean2, new Converter() {

			@Override
			public Object convert(Object p0, Class p1, Object p2) {
				System.out.println();
				System.out.println("进行了转换");
				System.out.println("p0Object:" + p0 + "\t\tp0Object:ClassName:" + p0.getClass().getName());
				System.out.println("p1Class:" + p1 + "\tp1Class:ClassName:" + p1.getClass().getName());
				System.out.println("p2Object:" + p2 + "\t\tp2Object:ClassName:" + p2.getClass().getName());
				System.out.println("=====================");
				System.out.println("转换结束");
				System.out.println();
				return p0;
			}
		});
		System.out.println("*********************");
		System.out.println(camelBean2);
	}
	
	@Test
	public void testBeanCopierConverterMethodName() {
		BeanCopier copy = BeanCopier.create(StudentCamelBean.class, StudentUnderLineBean.class, true);
		StudentCamelBean camelBean = new StudentCamelBean("alieckxie", 25, "天气不错");
		StudentUnderLineBean camelBean2 = new StudentUnderLineBean();
		System.out.println(camelBean2);
		System.out.println("*********************");
		copy.copy(camelBean, camelBean2, new Converter() {
			
			@Override
			public Object convert(Object p0, Class p1, Object p2) {
				System.out.println();
				System.out.println("进行了转换");
				System.out.println("p0Object:" + p0 + "\t\tp0Object:ClassName:" + p0.getClass().getName());
				System.out.println("p1Class:" + p1 + "\tp1Class:ClassName:" + p1.getClass().getName());
				System.out.println("p2Object:" + p2 + "\t\tp2Object:ClassName:" + p2.getClass().getName());
				System.out.println("=====================");
				System.out.println("转换结束");
				System.out.println();
				return p0;
			}
		});
		System.out.println("*********************");
		System.out.println(camelBean2);
	}

}
