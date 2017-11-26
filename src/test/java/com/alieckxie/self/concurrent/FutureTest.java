package com.alieckxie.self.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

public class FutureTest {

	@Test
	public void testSimpleFuture() throws InterruptedException, ExecutionException {
		ExecutorService threadPool = Executors.newCachedThreadPool();
		Future<String> submit = threadPool.submit(new Callable<String>() {

			@Override
			public String call() throws Exception {
				System.out.println("执行任务一。。。");
				Thread.sleep(1000);
				System.out.println("执行任务二。。。");
				Thread.sleep(1000);
				System.out.println("执行任务三。。。");
				Thread.sleep(10000);
				return "任务结束。。。";
			}
		});
		String string = submit.get();
		System.out.println(string);
	}
	
	@Test
	public void testRunnableFuture() throws InterruptedException, ExecutionException {
		ExecutorService threadPool = Executors.newCachedThreadPool();
		Future<?> future = threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("线程执行前。");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("线程执行完毕");
			}
		});
		System.out.println("1");
		System.out.println("2");
		System.out.println("3");
		System.out.println("4");
		Thread.sleep(500);
		System.out.println("5");
		System.out.println("6");
		System.out.println("7");
		System.out.println(future.get());
	}
	
}
