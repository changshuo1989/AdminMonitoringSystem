package com.hrs.changshuo.demo.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import com.hrs.changshuo.demo.db.DataSourceHolder;

@Aspect
@Configuration
public class DataSourceAspect {
	@Pointcut("execution(* com.hrs.changshuo.demo.controller.env..*.*(..))")
	private void anyMethod(){}
	
	@Before("anyMethod()")
	public void testBefore(){
		//DataSourceHolder.setDataSource("env1");
		System.out.println("before method!");
	}
	
	@After("anyMethod()")
	public void testAfter(){
		System.out.println("after method!");
	}
	
}
