package org.ezen.ex02.mapper;

import java.util.List;

import org.ezen.ex02.domain.BoardAttachVO;

//게시판에서 사용하는 첨부파일 처리 인터페이스 매퍼
public interface BoardAttachMapper {
	
	//첨부물 BoardAttachVO객체를 테이블에 생성(insert)
	public void insert(BoardAttachVO vo);
	
	//pk인 uuid로 지정된 첨부 파일 레코드 삭제
	public void delete(String uuid);
	
	//게시글 번호 bno에 속하는 첨부물 리스트 반환
	public List<BoardAttachVO> findByBno(Long bno);
	
	//특정 게시글에 속하는 첨부 파일 모두 제거
	public void deleteAll(Long bno);
	
	//어제 날짜의 첨부 파일 반환
	public List<BoardAttachVO> getOldFiles();
}
