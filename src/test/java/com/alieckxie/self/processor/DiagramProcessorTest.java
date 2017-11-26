package com.alieckxie.self.processor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;

import com.google.gson.Gson;

public class DiagramProcessorTest {

	@Test
	public void testSetData() {
		/*
		 * BigDecimal ltst = (BigDecimal) map.get("ltst"); BigDecimal vol =
		 * (BigDecimal) map.get("vol"); Date mktUpTm = (Date)
		 * map.get("mktUpTm");
		 */
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Calendar calendar = Calendar.getInstance();
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ltst", new BigDecimal(10 + i));
			map.put("vol", new BigDecimal(1000 + i * 100));
			int nextInt = random.nextInt(2);
			calendar.add(Calendar.MINUTE, nextInt);
			map.put("mktUpTm", calendar.getTime());
			list.add(map);
		}

		DiagramDataProcessor processor = new DiagramDataProcessor("ts_minuteSlice.USD/CNY", "123");
		long start = System.nanoTime();
		processor.addRawData(list);
		System.out.println((System.nanoTime() - start)/1000000D);
		System.out.println(new Gson().toJson(processor));
	}

}
