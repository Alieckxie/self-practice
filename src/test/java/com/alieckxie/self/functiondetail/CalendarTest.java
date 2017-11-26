package com.alieckxie.self.functiondetail;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class CalendarTest {

	@Test
	public void testCalendarAddDayOfWeek() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 5);
		if (Calendar.SATURDAY == calendar.get(Calendar.DAY_OF_WEEK)) {
			calendar.add(Calendar.DATE, 2);
		} else if (Calendar.SUNDAY == calendar.get(Calendar.DAY_OF_WEEK)) {
			calendar.add(Calendar.DATE, 1);
		}
		System.out.println(calendar.getTime());
	}

	@Test
	public void testGetHourDiff() {
		Calendar cal1 = Calendar.getInstance();
		cal1.set(2016, 11, 31, 23, 58, 45);
		Calendar cal2 = Calendar.getInstance();
		// cal2.add(Calendar.HOUR_OF_DAY, 1);
		cal2.set(2017, 0, 1, 3, 7, 45);
		int diff = getHourDiff(cal1.getTime(), cal2.getTime());
		System.out.println(diff);

	}

	@Test
	public void testGetMinuteDiff() {
		Calendar cal1 = Calendar.getInstance();
		cal1.set(2016, 12, 31, 23, 58, 45);
		Calendar cal2 = Calendar.getInstance();
		// cal2.add(Calendar.HOUR_OF_DAY, 1);
		cal2.set(2017, 1, 1, 3, 7, 45);
		long start = System.nanoTime();
		for (int i = 0; i < 10000; i++) {
			int diff = getMinuteDiff(cal1.getTime(), cal2.getTime());
		}
		System.out.println((System.nanoTime() - start)/1000000D);

	}

	private int getHourDiff(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		if (Math.abs(date1.getTime() - date2.getTime()) > 24 * 3600 * 1000) {
			throw new IllegalArgumentException("The time difference must in one day");
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		if (cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR) != 0) {
			cal1.add(Calendar.DATE, 1);
			cal2.add(Calendar.DATE, 1);
		}
		return (cal1.get(Calendar.DAY_OF_YEAR) - cal2.get(Calendar.DAY_OF_YEAR)) * 24
				+ (cal1.get(Calendar.HOUR_OF_DAY) - cal2.get(Calendar.HOUR_OF_DAY));
	}

	private int getMinuteDiff(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		if (Math.abs(date1.getTime() - date2.getTime()) > 24 * 3600 * 1000) {
			throw new IllegalArgumentException("The time difference must in one day");
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		if (cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR) != 0) {
			cal1.add(Calendar.DATE, 1);
			cal2.add(Calendar.DATE, 1);
		}
		return (cal1.get(Calendar.DAY_OF_YEAR) - cal2.get(Calendar.DAY_OF_YEAR)) * 24 * 60
				+ ((cal1.get(Calendar.HOUR_OF_DAY) - cal2.get(Calendar.HOUR_OF_DAY)) * 60
						+ (cal1.get(Calendar.MINUTE) - cal2.get(Calendar.MINUTE)));
	}

}
