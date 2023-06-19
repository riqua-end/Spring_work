package org.ezen.ex02.mapper;

import java.util.stream.IntStream;

import org.ezen.ex02.domain.ReplyVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Log4j
public class ReplyMapperTests {

	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;
	
	private Long[] bnoArr = { 56L,57L,58L,59L,60L }; //게시글 번호 배열
	/*
	@Test
	//mapper객체 주입 여부 확인
	public void testMapper() {
		
		log.info("mapper-----------" + mapper);
	}
	*/
	
	@Test
	public void testCreate() {
		
		IntStream.rangeClosed(1, 10).forEach(i -> {
			//rangeClosed(1,10)은 1부터 10까지 1씩 증가하면서 선택
			//forEach는 1부터 10까지 반복
			ReplyVO vo = new ReplyVO();
			
			//게시물의 번호
			vo.setBno(bnoArr[i % 5]);
			vo.setReply("댓글 테스트 " + i);
			vo.setReplyer("replyer " + i);
			
			mapper.insert(vo);
		});
	}
	
}
