package org.ezen.ex02.service;

import java.util.List;

import org.ezen.ex02.domain.BoardVO;
import org.ezen.ex02.domain.Criteria;

public interface BoardService {
	
	public void register(BoardVO board); //Create
	//Create BoardMapper는 public void insert(BoardVO board);
	
	public BoardVO get(Long bno); //Read
	//Read BoardMapper는 public BoardVO read(Long bno);
	
	public boolean modify(BoardVO board); //Update
	//Update BoardMapper는 public int update(BoardVO board);
	
	public boolean remove(Long bno); //delete
	//delete BoardMapper는 public int delete(Long bno);
	
	//페이지 미처리
	//public List<BoardVO> getList(); //목록 select
	//목록 select BoardMapper는 public List<BoardVO> getList();
	
	//페이지 처리
	public List<BoardVO> getList(Criteria cri);
	
	//게시글 총 갯수 구하기
	public int getTotal(Criteria cri);
}
