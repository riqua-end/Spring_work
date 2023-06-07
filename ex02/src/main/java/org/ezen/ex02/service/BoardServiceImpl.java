package org.ezen.ex02.service;

import java.util.List;

import org.ezen.ex02.domain.BoardVO;
import org.ezen.ex02.mapper.BoardMapper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service //bean으로 자동 등록되기 위하여 필요
@AllArgsConstructor //모든 멤버 변수를 갖는 생성자
public class BoardServiceImpl implements BoardService {
	
	//Service에서 BoardMapper의 메서드를 호출해야 하므로 BoardMapper객체가 필요
	//멤버변수가 하나이고 그 멤버변수 하나를 사용하는 생성자를 @AllArgsConstructor로 만들어서 @Autowired없이 자동 주입
	//물론 생성자 없이 @Setter(onMethod_ = @Autowired)로 주입해도 된다.
	private BoardMapper mapper;

	@Override
	public void register(BoardVO board) {
		
		log.info("register....." + board);
		
		mapper.insertSelectKey(board);
	}

	@Override
	public BoardVO get(Long bno) {
		return null;
	}

	@Override
	public boolean modify(BoardVO board) {
		return false;
	}

	@Override
	public boolean remove(Long bno) {
		return false;
	}

	@Override
	public List<BoardVO> getList() {
		
		log.info("getList........");
		
		return mapper.getList();
	}

}
