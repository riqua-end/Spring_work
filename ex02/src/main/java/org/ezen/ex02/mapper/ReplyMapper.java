package org.ezen.ex02.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ezen.ex02.domain.Criteria;
import org.ezen.ex02.domain.ReplyVO;

public interface ReplyMapper {

	// 댓글 작성(CRUD중 Create)
	public int insert(ReplyVO vo);

	// 댓글 조회
	public ReplyVO read(Long rno);

	// 댓글 삭제(반환은 int 로 delete갯수)
	public int delete(Long rno);

	// 댓글 수정(반환은 int로 update갯수)
	public int update(ReplyVO reply);

	// 페이지 처리를 한 댓글 list
	// @Param은 2개 이상의 파라메터 사용시 사용 cri이름으로 Criteria cri객체 전달--바인딩 변수로 사용 #{}
	public List<ReplyVO> getListWithPaging(@Param("cri") Criteria cri, @Param("bno") Long bno);

	// 댓글 페이지 처리를 위한 댓글 개수
	public int getCountByBno(Long bno);
	
	//게시글 삭제시 해당 해시글의 댓글 모두 삭제 처리	
	public int deleteAll(Long bno);

}
