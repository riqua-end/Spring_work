package org.ezen.ex02.mapper;

import java.util.List;

import org.ezen.ex02.domain.BoardVO;
import org.ezen.ex02.domain.Criteria;
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

//mybatis이용한  tbl_board관련 테스트
public class BoardMapperTests {

	// Setter를 이용한 주입(BoardMapper를 주입 받음)
	@Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;

	/*
	 * @Test // BoardMapper인터페이스의 getList()메서드 테스트 public void testGetList() {
	 * mapper.getList().forEach(board -> log.info(board)); // mapper는 인터페이스의 참조 변수로
	 * 인터베이스를 구현을 안함 (즉 스프링이 자동 해줌) // List<BoardVO>로 반환 }
	 */

	/*
	 * @Test //BoardMapper인터페이스의 insert(BoardVO vo)테스트--bno값을 사전에 알수 없음 public void
	 * testInsert() {
	 * 
	 * BoardVO board = new BoardVO(); board.setTitle("새로 작성하는 글");
	 * board.setContent("새로 작성하는 내용"); board.setWriter("newbie");
	 * 
	 * mapper.insert(board);
	 * 
	 * log.info("kook " + board); }
	 */

	/*
	 * @Test //BoardMapper인터페이스의 insertSelectKey(BoardVO vo)테스트 public void
	 * testInsertSelectKey() {
	 * 
	 * BoardVO board = new BoardVO(); board.setTitle("새로 작성하는 글 select key");
	 * board.setContent("새로 작성하는 내용 select key"); board.setWriter("newbie");
	 * 
	 * mapper.insertSelectKey(board);
	 * 
	 * log.info("kook : " + board); }
	 */

	/*
	 * @Test // BoardMapper인터페이스의 public BoardVO read(Long bno) 테스트 public void
	 * testRead() {
	 * 
	 * // 존재하는 게시물 번호로 테스트 BoardVO board = mapper.read(5L);
	 * 
	 * log.info(board + " boardRead"); }
	 */

	/*
	@Test
	// BoardMapper인터페이스의 public int delete(Long bno)테스트
	public void testDelete() {
		log.info("DELETE COUNT: " + mapper.delete(7L));
	}
	*/

	/*
	@Test
	// BoardMapper인터페이스의 public int update(BoardVO board) 테스트
	public void testUpdate() {

		BoardVO board = new BoardVO();
		// 실행전 존재하는 번호인지 확인할 것
		board.setBno(5L);
		board.setTitle("수정된 제목11");
		board.setContent("수정된 내용11");
		board.setWriter("user11");

		// 반환값 count는 update성공한 갯수
		int count = mapper.update(board);
		log.info("UPDATE COUNT: " + count);

	}
	*/
	
	/*
	// 기본으로 페이지 1번 반환 
	@Test
	public void testPaging() {

		Criteria cri = new Criteria();

		List<BoardVO> list = mapper.getListWithPaging(cri);

		list.forEach(board -> log.info(board));
		
		System.out.println("boardPage" + list);

	}
	*/
	
	//페이지 번호 3번 반환
	/*
	@Test
	public void testPaging() {

		Criteria cri = new Criteria();
		
	    //10개씩 3번페이지 
	    cri.setPageNum(3);
	    cri.setAmount(10);


		List<BoardVO> list = mapper.getListWithPaging(cri);

		list.forEach(board -> log.info(board));
		
		System.out.println("boardPage" + list);

	}
	*/
	
	@Test
    public void testSearch() {
	  //검색 미반영 list와 방식은 동일(검색 조건에 맞는 것만 리스트됨, 검색 조건이 없으면 일반 리스트)	
      Criteria cri = new Criteria();
      cri.setKeyword("새로");
      cri.setType("TCW"); //제목과 내용 검색 T,C,W,TC,TW,CW,TWC

      List<BoardVO> list = mapper.getListWithPaging(cri);

      list.forEach(board -> log.info(board));
    }

}
