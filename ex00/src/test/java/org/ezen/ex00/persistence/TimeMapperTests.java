package org.ezen.ex00.persistence;

import org.ezen.ex00.mapper.TimeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class TimeMapperTests {
	
	@Setter(onMethod_ = @Autowired)
	private TimeMapper timeMapper; //인터페이스 참조 변수 주입(mapper패키지의 인터페이스는 bean으로 관리)
	
	@Test
	//mybatis @Select어노테이션 사용
	public void testGetTime() {
		log.info(timeMapper.getClass());
		log.info(timeMapper.getTime());
		//getTime추상메서드의 어노테이션 @Select("Select sysdate from dual")를 실행
	}
}
