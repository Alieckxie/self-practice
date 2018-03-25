package com.alieckxie.self.aop.misc;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint.StaticPart;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.SourceLocation;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SimpleAopAdvice {

	@Pointcut("execution(** com.alieckxie.self.aop.misc.*.say*(String))")
	public void pointcut() {};
	
	@Before("pointcut()")
	public void before() {
		System.out.println("Say Before =====================");
	}
	
	@After("pointcut()")
	public void after() {
		System.out.println("Say After =====================");
	}
	
	@Around("pointcut()")
	public void around(ProceedingJoinPoint pjp) {
		System.out.println("Say Around Start=====================");
		Object[] args = pjp.getArgs();
		System.out.println("args:" + Arrays.toString(args));
		String kind = pjp.getKind();
		System.out.println("kind:" + kind);
		Signature signature = pjp.getSignature();
		System.out.println("signature:" + signature);
		SourceLocation sourceLocation = pjp.getSourceLocation();
		System.out.println("sourceLocation:" + sourceLocation);
		StaticPart staticPart = pjp.getStaticPart();
		System.out.println("staticPart:" + staticPart);
		Object target = pjp.getTarget();
		System.out.println("target:" + target);
		Object this1 = pjp.getThis();
		System.out.println("this1" + this1);
		System.out.println("Start proceed.....");
		try {
			pjp.proceed();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("End proceed.....");
		System.out.println("Start proceed with args.....");
		if (args.length > 0) {
			args[0] = args[0] + "ASD";
		}
		try {
			pjp.proceed(args);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("End proceed with args.....");
		String longString = pjp.toLongString();
		System.out.println("longString:" + longString);
		String shortString = pjp.toShortString();
		System.out.println("shortString:" + shortString);
		System.out.println("toString:" + pjp);
		System.out.println("Say Around End=====================");
	}
}
