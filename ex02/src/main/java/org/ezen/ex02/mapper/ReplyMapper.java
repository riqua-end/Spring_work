package org.ezen.ex02.mapper;

import org.ezen.ex02.domain.ReplyVO;

public interface ReplyMapper {
	//댓글 작성 (CRUD중 Create)
	public int insert(ReplyVO vo);
	
	//댓글 조회 Read
	public ReplyVO read(Long rno);
	
	//댓글 삭제 delete(반환은 int로 delete갯수)
	public int delete(Long rno);
	
	//댓글 수정(반환은 int로 update갯수)
	public int update(ReplyVO reply);
}
