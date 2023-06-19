package org.ezen.ex02.mapper;

import org.ezen.ex02.domain.ReplyVO;

public interface ReplyMapper {
	//댓글 작성 (CRUD중 Create)
	public int insert(ReplyVO vo);
	
}
