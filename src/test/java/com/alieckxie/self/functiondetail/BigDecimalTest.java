package com.alieckxie.self.functiondetail;

import java.math.BigDecimal;

import org.junit.Test;

import com.alieckxie.self.bean.BigDecimalBean;
import com.google.gson.Gson;

public class BigDecimalTest {

	@Test
	public void testToPlainString() {
		BigDecimal decimal = new BigDecimal("3.1");
		BigDecimal divide = decimal.divide(new BigDecimal(2.3D), 4, BigDecimal.ROUND_HALF_UP);
		System.out.println(divide);
		System.out.println(divide.toPlainString());
		System.out.println(new Gson().toJson(divide));
		System.out.println(new BigDecimal("234.4321").divide(new BigDecimal("100000")));
	}

	@Test
	public void testBigDecimalSetScale() {
		BigDecimalBean bean = new BigDecimalBean();
		bean.setDecimalA(new BigDecimal(2.412543476896987E3D));
		bean.setDecimalB(BigDecimal.valueOf(19301.4132122));
		bean.setDecimalC(new BigDecimal("769712.4213412"));
		System.out.println(bean.getDecimalA());
		System.out.println(bean.getDecimalB());
		System.out.println(bean.getDecimalC());
		System.out.println(new Gson().toJson(bean));
//		bean.setDecimalA(bean.getDecimalA().setScale(4, BigDecimal.ROUND_HALF_UP));
//		bean.setDecimalB(bean.getDecimalB().setScale(4, BigDecimal.ROUND_HALF_UP));
//		bean.setDecimalC(bean.getDecimalC().setScale(4, BigDecimal.ROUND_HALF_UP));
		System.out.println(bean.getDecimalA());
		System.out.println(bean.getDecimalB());
		System.out.println(bean.getDecimalC());
		
		BigDecimal setScale = bean.getDecimalA().setScale(8, BigDecimal.ROUND_HALF_UP);
		System.out.println("SetScale:" + setScale);
		System.out.println("SetScalePrecision:" + setScale.precision());
		System.out.println("SetScalePrecision2:" + bean.getDecimalA().precision());
		System.out.println(bean.getDecimalA());
		BigDecimal bigDecimal = new BigDecimal("2.314427896767");
		System.out.println(bigDecimal.precision());
	}
	
	@Test
	public void testAddNull(){
//		BigDecimal add = BigDecimal.ZERO.add(null);
		Object asd = BigDecimal.ZERO;
		BigDecimal decimal = new BigDecimal(asd.toString());
		System.out.println(decimal);
		BigDecimalBean bean = new BigDecimalBean();
		bean.setDecimalA(new BigDecimal("3641784691.547893206758290"));
		System.out.println(bean.getDecimalA().setScale(4, BigDecimal.ROUND_HALF_UP));
		System.out.println(bean.getDecimalA());
	}

}
