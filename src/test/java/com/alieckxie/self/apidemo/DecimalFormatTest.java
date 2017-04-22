package com.alieckxie.self.apidemo;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.junit.Test;

public class DecimalFormatTest {

	@Test
	public void testDecimalFormat() {

		// ---------------------------------------------
		// 定义一个数字格式化对象，格式化模板为".##"，即保留2位小数.
		DecimalFormat a = new DecimalFormat(".##");
		String s = a.format(333.335);
		System.err.println(s);
		// 说明：如果小数点后面不够2位小数，不会补零.参见Rounding小节
		// ---------------------------------------------

		// -----------------------------------------------
		// 可以在运行时刻用函数applyPattern(String)修改格式模板
		// 保留2位小数，如果小数点后面不够2位小数会补零
		a.applyPattern(",##0.");
		s = a.format(333);
		System.err.println("----"+s);
		// ------------------------------------------------

		// ------------------------------------------------
		// 添加千分号
		a.applyPattern(".##\u2030");
		s = a.format(0.78934);
		System.err.println(s);// 添加千位符后,小数会进三位并加上千位符
		// ------------------------------------------------

		// ------------------------------------------------
		// 添加百分号
		a.applyPattern(".##%");
		s = a.format(0.78645);
		System.err.println(s);
		// ------------------------------------------------

		// ------------------------------------------------
		// 添加前、后修饰字符串，记得要用单引号括起来
		a.applyPattern("'这是我的钱$',###.###'美圆'");
		s = a.format(33333443.3333D);
		System.err.println(s);
		// ------------------------------------------------

		// ------------------------------------------------
		// 添加货币表示符号(不同的国家，添加的符号不一样
		a.applyPattern("\u00A4");
		s = a.format(34);
		System.err.println(s);
		// ------------------------------------------------

		// -----------------------------------------------
		// 定义正负数模板,记得要用分号隔开
		a.applyPattern("#0.0");
		Object number = new BigDecimal("0.33");
		s = a.format(number);
		System.err.println(s);
		s = a.format(-0.33);
		System.err.println(s);
		// -----------------------------------------------

		// 综合运用，正负数的不同前后缀
		String pattern = "'my moneny',###.##'RMB';'ur money',###.##'US'";
		a.applyPattern(pattern);
		System.out.println(a.format(1223233.456));
	}
}
