package com.alieckxie.self.beancopy;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.junit.Test;

import com.alieckxie.self.bean.BigDecimalBean;
import com.alieckxie.self.bean.DoubleBean;

public class BigDecimalCopyTest {

	@Test
	public void testCopyDouble2BigDecimal() throws IllegalAccessException, InvocationTargetException {
		BigDecimalBean bigDecimalBean = new BigDecimalBean();
		DoubleBean doubleBean = new DoubleBean();
		doubleBean.setDecimalA(123.475894540326739128413);
		doubleBean.setDecimalB(125.48540175000000000000000000000000000378);
		doubleBean.setDecimalC(126.454325742986431782328);
		System.out.println(doubleBean);
		System.out.println(bigDecimalBean);
		BeanUtils.copyProperties(bigDecimalBean, doubleBean);
		System.out.println(bigDecimalBean);
		System.out.println(bigDecimalBean.getDecimalA().toPlainString());
		System.out.println(bigDecimalBean.getDecimalB().toPlainString());
		System.out.println(bigDecimalBean.getDecimalC().toPlainString());
		System.out.println(BigDecimal.valueOf(123.475894540326739128413D).toPlainString());
	}

	@Test
	public void testCopyNullBigDecimal() throws IllegalAccessException, InvocationTargetException {
		BigDecimalBean bigDecimalBean = new BigDecimalBean();
		bigDecimalBean.setDecimalA(BigDecimal.TEN);
		BigDecimalBean bigDecimalBean2 = new BigDecimalBean();
		BeanUtils.copyProperties(bigDecimalBean2, bigDecimalBean);
	}

	static	{

		ConvertUtils.register(new DateConverter(null), java.util.Date.class);

		ConvertUtils.register(new DateConverter(null), java.sql.Date.class);

		ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);

	}
}
