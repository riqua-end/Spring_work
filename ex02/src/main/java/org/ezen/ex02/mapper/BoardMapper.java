package org.ezen.ex02.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.ezen.ex02.domain.BoardVO;

public interface BoardMapper {
	
	//mybatis의 @select 어노테이션으로 처리
	//SQL문을 작성할때 ;이 없어야 한다
	//@Select("select * from tbl_board where bno > 0")
	//주석 달면 xml을 이용하여 처리
	//mybatis는 select시 ResultSet으로 반환되는것을 자바 컬렉션으로 반환
	public List<BoardVO> getList();
	
	//PK값인 tbl_board테이블의 bno에 들어가는 seq_board의 nextval값을 미리 알필요 없는 경우
	public void insert(BoardVO board);
	
	//PK값인 tbl_board테이블의 bno에 들어가는 seq_board의 nextval값을 미리 알고 있는 경우
	public Integer insertSelectKey(BoardVO board);
	
	//PK값인 bno를 검색 조건으로 하여 일치하는 하나의 레코드를 매핑되는 BoardVO객체로 반환
	public BoardVO read(Long bno);
	
}
