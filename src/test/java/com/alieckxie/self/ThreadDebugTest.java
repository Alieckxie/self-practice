package com.alieckxie.self;

import org.junit.Test;

public class ThreadDebugTest {

	@Test
	public void testThreadDebug1() throws Exception {
		new Thread("Alieckxie") {
			@Override
			public void run() {
				for (int i = 0; i < 50; i++) {
					System.out.println("这里是" + this.getName() + "线程第【" + i + "】次输出，接着将休眠1秒");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
		for (int i = 0; i < 50; i++) {
			System.out.println("这里是主线程第【" + i + "】次输出，接着将休眠1秒");
			Thread.sleep(1000);
		}
	}

}
