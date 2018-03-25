package com.alieckxie.self.aop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alieckxie.self.aop.misc.SimpleHelloWorld;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SimpleAopTest.class)
@Configuration
@EnableAspectJAutoProxy
@ComponentScan
public class SimpleAopTest {
	
	@Autowired
	private SimpleHelloWorld hello;

	@Test
	public void testAop1() {
		// 需要将被切的纳入Spring容器，也需要将切面也纳入到Spring中来管理，还需要开启Aspectj的自动代理注解
		hello.sayHello();
		// 需要将被切的纳入Spring容器，也需要将切面也纳入到Spring中来管理，还需要开启Aspectj的自动代理注解
		hello.sayHello("Alieckxie");
	}
}
