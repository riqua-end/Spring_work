package org.ezen.ex02.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ezen.ex02.domain.Criteria;
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
	
	//페이지 처리를 한 댓글 list
	//@Param은 2개 이상의 파라메터 사용시 사용 cri이름으로 Criteria cri객체 전달
	public List<ReplyVO> getListWithPaging(@Param("cri") Criteria cri, @Param("bno")Long bno);
}
