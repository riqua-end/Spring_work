package org.ezen.ex02.service;

import java.util.List;

import org.ezen.ex02.domain.BoardAttachVO;
import org.ezen.ex02.domain.BoardVO;
import org.ezen.ex02.domain.Criteria;
import org.ezen.ex02.mapper.BoardAttachMapper;
import org.ezen.ex02.mapper.BoardMapper;
import org.ezen.ex02.mapper.ReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service // bean으로 자동 등록되기 위햐여 필요(필수)
//@AllArgsConstructor(멤버변수 1개일때만 사용
// 모든 멤버 변수를 갖는 생성자
public class BoardServiceImpl implements BoardService {

	// Service에서 BoardMapper의 mrthod를 호출해야 하므로 BoardMapper객체가 필요
	// 멤버변수가 하나이고 그 멤버변수 하나를 사용하는 생성자를 @AllArgsConstructor로 만들어서 @Autowired없이 자동 주입
	// 물론 생성자 없이 @Setter(onMethod_ = @Autowired)로 주입해도 된다
	//private BoardMapper mapper;
	
	@Setter(onMethod_ = @Autowired) 
	//4.3이상 부터는 멤버변수 하나를 사용하는 생성자가 있으면 선언만 해도 자동 주입 
	private BoardMapper mapper;
	
	@Setter(onMethod_ = @Autowired)
	private BoardAttachMapper attachMapper;
	
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper replyMapper;
	
	//게시판만 처리
	/*
	@Override
	public void register(BoardVO board) {

		log.info("register......" + board);

		mapper.insertSelectKey(board);

	}
	*/
	
	//게시판 + 첨부 파일 처리
	@Transactional
	@Override
	public void register(BoardVO board) {

		log.info("register......" + board);

		mapper.insertSelectKey(board);

		if (board.getAttachList() == null || board.getAttachList().size() <= 0) {
			return;
		}

		board.getAttachList().forEach(attach -> {

			attach.setBno(board.getBno());
			attachMapper.insert(attach);
		});
	}
	
	@Override
	public BoardVO get(Long bno) {
		
		log.info("get......" + bno);

		return mapper.read(bno);
		
	}
	
	//첨부파일 미 고려
	/*
	@Override
	public boolean modify(BoardVO board) {

		log.info("modify......" + board);

		return mapper.update(board) == 1;
	}
	*/
	
	//첨부파일 처리
	@Transactional
	@Override
	public boolean modify(BoardVO board) {

		log.info("modify......" + board);

		attachMapper.deleteAll(board.getBno()); //기존은 bno에 일치하는 것 모두 삭제

		boolean modifyResult = mapper.update(board) == 1;
				
		if (modifyResult && board.getAttachList() != null) {
		
			board.getAttachList().forEach(attach -> {				
				attach.setBno(board.getBno());
				attachMapper.insert(attach);
			});
		}		

		return modifyResult;
	}

	//댓글,첨부파일 미포함 삭제
	/*
	@Override
	public boolean remove(Long bno) {

		log.info("remove...." + bno);

		return mapper.delete(bno) == 1;
	}
	*/
	
	//게시글,댓글,첨부 파일 일괄 삭제
	@Transactional
	@Override
	public boolean remove(Long bno) {

		log.info("remove...." + bno);
		
		//삭제시 tbl_board의 bno를 외래키로 사용하는 자식 테이블부터 삭제
		replyMapper.deleteAll(bno);

		attachMapper.deleteAll(bno);

		return mapper.delete(bno) == 1;
	}

	//페이지 미처리
	/*
	@Override
	public List<BoardVO> getList() {

		log.info("getList..........");
		return mapper.getList();
	}
	*/
	
	//페이지 처리
	@Override
	public List<BoardVO> getList(Criteria cri) {

		log.info("get List with criteria: " + cri);

		return mapper.getListWithPaging(cri);
	}
	
	//게시글 총갯수
	@Override
	public int getTotal(Criteria cri) {

		log.info("get total count");
		return mapper.getTotalCount(cri);
	}
	
	//게시판 조회에서 사용하는 첨부물 처리 메서드
	@Override
	public List<BoardAttachVO> getAttachList(Long bno) {

		log.info("get Attach list by bno" + bno);

		return attachMapper.findByBno(bno);
	}

}
