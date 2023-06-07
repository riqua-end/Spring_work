package org.ezen.ex02.persistence;

import static org.junit.Assert.fail;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.ezen.ex02.persistence.DataSourceTests;
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
public class DataSourceTests {
	
	@Setter(onMethod_ = {@Autowired})
	private DataSource dataSource;
	
	@Setter(onMethod_= {@Autowired})
	private SqlSessionFactory sqlSessionFactory;
	
	/*
	@Test
	public void testConnection() {
		
		try (Connection con = dataSource.getConnection()) {
			
			log.info(con);
			
		}
		catch (Exception e) {
			fail(e.getMessage());
			// fail메서드도 JUnit의 메서드로 실패 메세지를 출력
		}
	}
	*/
	
	@Test
	public void testMyBatis() {
		
		try(
			SqlSession session = sqlSessionFactory.openSession();
				//SqlSession은 인터페이스로 mybatis에 사용됨
			Connection con = session.getConnection();) {

			log.info(session);
			log.info(con);

		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
}
