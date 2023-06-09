package org.ezen.ex04.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Aspect //Advice들이 정의된 클래스
@Log4j
@Component //빈으로 등록
public class LogAdvice {

	//Advice의 실행 시점을 나타내는 @Before어노테이션은 핵심(타겟)이 실행되기전 실행하는 어드바이스
	// 괄호안의 PointCut을 나타내는 표현식으로 execution은 메서드를 지정
	@Before("execution(* org.ezen.ex04.service.SampleService*.*(..))")
	public void logBefore() {
		
		log.info("==================");
	}
	
	
	@Before("execution(* org.ezen.ex04.service.SampleService*.doAdd(String, String)) && args(str1,str2)")
	//핵심(타겟) 메서드의 사용 파라메터값을 어드바이스의 파라메터로 전달 받아서 사용
	public void logBeforeWithParam(String str1,String str2) {
		
		log.info("str1 : " + str1);
		log.info("str2 : " + str2);
	}
	
	//핵심 메서드
	@AfterThrowing(pointcut = "execution(* org.ezen.ex04.service.SampleService*.*(..))", throwing = "exception")
	public void logException(Exception exception) {
		
		log.info("Exception....!!!!");
		log.info("exception : " + exception);
	}
	
	//@Before어드바이스보다 먼저 실행
	@Around("execution(* org.ezen.ex04.service.SampleService*.*(..))")
	public Object logTime(ProceedingJoinPoint pjp) {
		
		long start = System.currentTimeMillis();
		
		log.info("target : " + pjp.getTarget());
		log.info("param : " + Arrays.toString(pjp.getArgs()));
		
		//invoke method
		
		Object result = null;
		
		try {
			result = pjp.proceed();
		}
		catch (Throwable e) {
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		
		log.info("TIME : " + (end - start));
		
		return result;
	}
}
