package com.alieckxie.self.util.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RealCacheTaskThreadPool {

	private ThreadPoolExecutor executor;
	private LinkedBlockingQueue<Runnable> rejectedExecutionQueue = new LinkedBlockingQueue<Runnable>();
	
	public RealCacheTaskThreadPool(int maximumPoolSize) {
		this(1, maximumPoolSize, 60L, TimeUnit.SECONDS);
	}

	public RealCacheTaskThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit) {
		this(corePoolSize, maximumPoolSize, keepAliveTime, unit, new SynchronousQueue<Runnable>(),
				Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
	}
	
	public RealCacheTaskThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
		this.executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
				threadFactory, handler);
	}
	
	/**
	 * 需要包装这个command
	 * @param command
	 */
	public void execute(Runnable command) {
		executor.execute(command);
	}

}
