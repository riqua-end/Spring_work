package org.ezen.ex04.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@Log4j
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class SampleServiceTests {

	@Setter(onMethod_ = @Autowired)
	private SampleService service;
	
	/*
	@Test
	public void testClass() {
		
		log.info(service);
		log.info(service.getClass().getName());
		//service객체의 클래스를 반환해서 이름을 얻어냄
		//AOP의 횡단관심사인 advice의 대상인 핵심 로직은 advice와 핵심로직이 결합된 Proxy가 됨
	}
	*/
	
	@Test
	public void testAdd() throws Exception {
		
		log.info(service.doAdd("123", "456"));
	}
	
}
