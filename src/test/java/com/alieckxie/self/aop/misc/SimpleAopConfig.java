package com.alieckxie.self.aop.misc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleAopConfig {

	@Bean
	public SimpleHelloWorld simpleHelloWorld() {
		return new SimpleHelloWorld();
	}
}
