package org.ezen.ex02.mapper;

import org.ezen.ex02.domain.BoardVO;
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

//mybatis이용한 tbl_board관련 테스트
public class BoardMapperTests {
	
	//Setter를 이용한 주입 (BoardMapper를 주입 받음)
	@Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;
	
	/*
	@Test
	//BoardMapper인터페이스의 getList()메서드 테스트
	public void testGetList() {
		
		mapper.getList().forEach(board -> log.info(board));
		//mapper는 인터페이스의 참조 변수로 인터페이스를 구현을 안함 (즉 스프링이 자동으로 해줌)
		//List<BoardVO>로 반환
	}
	*/
	
	/*
	@Test
	//BoardMapper인터페이스의 insert(BoardVO vo)테스트
	public void testInsert() {
		
		BoardVO board = new BoardVO();
		board.setTitle("새로 작성하는 글");
		board.setContent("새로 작성하는 내용");
		board.setWriter("newbie");
		
		mapper.insert(board);
		
		log.info("kim" + board);
	}
	*/
	
	@Test
	//BoardMapper인터페이스의 insertSelectKey(BoardVO vo)테스트
	public void testInsertSelectKey() {
		
		BoardVO board = new BoardVO();
		board.setTitle("새로 작성하는 글 select key");
		board.setContent("새로 작성하는 내용 select key");
		board.setWriter("newbie");
		
		mapper.insertSelectKey(board);
		
		log.info("kim" + board);
		
	}
}
