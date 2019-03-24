package com.alieckxie.self.algorithm;

import org.junit.Test;

import com.alieckxie.self.bean.GeneralizedList;
import com.alieckxie.self.bean.GeneralizedList.ElementTag;
import com.google.gson.Gson;

public class GeneralizedListTest {

	@Test
	public void testGeneralizedListSet() {
		GeneralizedList<String> gl = new GeneralizedList<String>();
		gl.setElement("first Element");
		gl.addValue("overwrited Data", ElementTag.ATOM);
		gl.addValue("1 Data", ElementTag.ATOM);
		gl.addValue("2 Data", ElementTag.LIST);
		GeneralizedList<String> list = gl.getElementList().get(2);
		list.addValue("2 ATOM Data", ElementTag.ATOM);
		list.addValue("2 LIST Data", ElementTag.LIST);
		gl.addValue("3 Data", ElementTag.LIST);
		System.out.println(new Gson().toJson(gl));
		System.out.println(gl);
	}
}
