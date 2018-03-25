package com.alieckxie.self.concurrent;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;

public class InterruptThreadTest {
	
	public static AtomicLong atomicCount = new AtomicLong();
	public static long count = 0;

	@Test
	public void testTerminate() {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		System.out.println(atomicCount);
		for (int i = 1; i <= 10; i++) {
			int a = i;
			executor.execute(new Runnable() {
				
				@Override
				public void run() {
					System.out.println("任务" + a + "开始运行");
					String c = "A";
					String b = "";
					for (int j = 0; j < 10000; j++) {
						atomicCount.incrementAndGet();
						count++;
						b = b + c;
					}
					System.out.println("任务" + a + "运行结束");
					System.err.println("任务" + c + a + "运行结束");
				}
			});
		}
		try {
			TimeUnit.MILLISECONDS.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Runnable> shutdownNow = executor.shutdownNow();
		System.out.println(shutdownNow.size());
		System.out.println("atomicCount" + atomicCount);
		System.out.println("count:" + count);
	}
	
	@Test
	public void testCount() {
		Random random = new Random();
		int nextInt = random.nextInt(10001);
		AtomicLong atomicLong = new AtomicLong();
		long count = 0;
		for (int i = 0; i < 10000; i++) {
			atomicLong.getAndIncrement();
			count++;
			if (i == nextInt) {
				break;
			}
		}
		System.out.println(atomicLong);
		System.out.println(count);
	}
}
