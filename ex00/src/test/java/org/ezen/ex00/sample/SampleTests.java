package org.ezen.ex00.sample;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class) //스프링용 테스트 클래스를 이용하여 테스트를 실행
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml") //스프링 응용프로그램의 설정
@Log4j //Lombok를 이용한 Logger객체 log를 등록
public class SampleTests {
	
	@Setter(onMethod_ = {@Autowired}) //주입받아서 사용 (new REstaurant()로 안 만듦)
	private Restaurant restaurant;
	
	@Test //JUnit에서 테스트 대상임을 표시함
	public void testExist() {
		
		//assertNotNull(빈)은 주입된 빈이 null이 아니어야 테스트가 성공임을 나타냄
		assertNotNull(restaurant);
		
		log.info(restaurant);
		log.info("----------------------------");
		log.info(restaurant.getChef());
	}
}
