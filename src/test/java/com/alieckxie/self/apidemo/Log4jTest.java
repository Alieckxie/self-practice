package com.alieckxie.self.apidemo;

import org.apache.log4j.Logger;
import org.junit.Test;

public class Log4jTest {
	
	private static final Logger logger = Logger.getLogger(Log4jTest.class);

	@Test
	public void testLog4j1(){
		logger.debug("this is debug!");
	}

}
