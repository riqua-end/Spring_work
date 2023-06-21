package org.ezen.ex02.service;

import java.util.List;

import org.ezen.ex02.domain.Criteria;
import org.ezen.ex02.domain.ReplyPageDTO;
import org.ezen.ex02.domain.ReplyVO;

public interface ReplyService {

	//mapper의 insert (CRUD Create)
	public int register(ReplyVO vo);
	
	//mapper의 read(CRUD Read)
	public ReplyVO get(Long rno);
	
	//mapper의 update(CRUD Update)
	public int modify(ReplyVO vo);
	
	//mapper의 delete(CRUD Delete)
	public int remove(Long rno);
	
	//mapper의 getListWithPaging
	public List<ReplyVO> getList(Criteria cri,Long bno);
	
	//댓글의 수와 댓글의 목록을 같이 처리하는 DTO반환 메서드
	public ReplyPageDTO getListPage(Criteria cri,Long bno);
	
}
