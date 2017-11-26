package com.alieckxie.self.concurrent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

import javax.management.RuntimeErrorException;

import org.junit.Test;

public class ScheduleThreadPoolTest {

	@Test
	public void testScheduledThreadPool() throws InterruptedException {
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ScheduledExecutorService exec = Executors.newScheduledThreadPool(2); // new ScheduledThreadPoolExecutor(1);
//		ScheduledExecutorService newSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
		ScheduledFuture<?> future = exec.scheduleAtFixedRate(new Runnable() {
			private int count = 0;
			
            public void run() {
                System.out.println("任务1：" + format.format(new Date()));
                count++;
                try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                if(count > 10){
                	throw new IllegalArgumentException("任务1出现异常");
                }
            }
        }, 1000, 500, TimeUnit.MILLISECONDS);
		exec.scheduleAtFixedRate(new Runnable() {
			public void run() {
				System.out.println("任务2：" + format.format(new Date()));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, 1000, 500, TimeUnit.MILLISECONDS);
		exec.scheduleWithFixedDelay(new Runnable() {
			public void run() {
				System.out.println("任务3：" + format.format(new Date()));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, 1000, 500, TimeUnit.MILLISECONDS);
		Object object = null;
		try {
			object = future.get();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(object);
		Thread.sleep(Long.MAX_VALUE);
	}
	
	@Test
	public void testFunction(){
		myFunctionDemo((a, b) -> {
			System.out.println(" lamda first param: " + a );
			System.out.println(" lamda second param: " + b);
			return "lamda 完事了……";
		});
	}
	
	private void myFunctionDemo(BiFunction<String, String, String> function){
		String t = "this is t";
		String u = "999";
		String apply = function.apply(t, u);
		System.out.println("this is apply:" + apply);
	}

}
