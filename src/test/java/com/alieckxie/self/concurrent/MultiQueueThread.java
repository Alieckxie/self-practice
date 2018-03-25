package com.alieckxie.self.concurrent;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class MultiQueueThread {

	private static final Map<String, ThreadPoolExecutor> pool = new LinkedHashMap<String, ThreadPoolExecutor>();
	static {
		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				System.out.println("pool.size():" + pool.size());
				for (Entry<String, ThreadPoolExecutor> entry : pool.entrySet()) {
					ThreadPoolExecutor threadPoolExecutor = entry.getValue();
					System.out.println("poolName:" + entry.getKey() + ", ActiveCount:"
							+ threadPoolExecutor.getActiveCount() + ", TaskCount:" + threadPoolExecutor.getTaskCount()
							+ ", QueueSize:" + threadPoolExecutor.getQueue().size());
				}
				System.err.println("-------------------");
				System.out.println();
			}
		}, 1000, 800, TimeUnit.MILLISECONDS);
	}

	@Test
	public void testHashMapThreadPool() throws InterruptedException {
		for (int j = 0; j < 10; j++) {
			for (int i = 1; i <= 10; i++) {
				String ord = String.valueOf(j) + "-" + String.valueOf(i);
				addTask("queue-" + i, new Runnable() {

					@Override
					public void run() {
						System.out.println("任务" + ord + "进入队列……");
						try {
							TimeUnit.SECONDS.sleep((long) (Math.random() * 10 + 1));
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.err.println("任务" + ord + "执行完毕……");
					}
				});
			}
		}
		Thread.currentThread().join(10000);
	}

	private void addTask(String queueName, Runnable task) {
		ThreadPoolExecutor executorService = pool.get(queueName);
		if (executorService == null) {
			executorService = getNewThreadPool();
			pool.put(queueName, executorService);
		}
		executorService.execute(task);
	}

	private ThreadPoolExecutor getNewThreadPool() {
		return new ThreadPoolExecutor(0, 1, 20, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
	}
}
