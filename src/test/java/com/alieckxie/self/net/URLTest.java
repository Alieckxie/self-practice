package com.alieckxie.self.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.junit.Test;

public class URLTest {

	@Test
	public void testURL1() throws IOException {
		URL url = new URL("http://www.baidu.com");
		InputStream inputStream = url.openStream();
	}
	
}
