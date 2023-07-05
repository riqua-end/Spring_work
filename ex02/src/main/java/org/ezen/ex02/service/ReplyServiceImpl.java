package org.ezen.ex02.service;

import java.util.List;

import org.ezen.ex02.domain.Criteria;
import org.ezen.ex02.domain.ReplyPageDTO;
import org.ezen.ex02.domain.ReplyVO;
import org.ezen.ex02.mapper.BoardMapper;
import org.ezen.ex02.mapper.ReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service //빈으로 자동 등록키 위해 필수로 지정
@Log4j
public class ReplyServiceImpl implements ReplyService {
	
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;
	
	//댓글수 고려시 BoardMapper의 메서드 사용하므로 추가
	@Setter(onMethod_ = @Autowired)
	private BoardMapper boardMapper;
	
	//댓글수 미고려
	/*
	@Override
	public int register(ReplyVO vo) {
		
		log.info("register......" + vo);
		return mapper.insert(vo);
	}
	*/
	
	//댓글수 고려
	@Transactional
	@Override
	public int register(ReplyVO vo) {

		log.info("register......" + vo);

		boardMapper.updateReplyCnt(vo.getBno(), 1); //댓글수 처리 메서드

		return mapper.insert(vo);

	}

	@Override
	public ReplyVO get(Long rno) {
		
		log.info("get......" + rno);

		return mapper.read(rno);
	}

	@Override
	public int modify(ReplyVO vo) {
		
		log.info("modify......" + vo);

		return mapper.update(vo);
	}

	//댓글수 미고려
	/*
	@Override
	public int remove(Long rno) {
		
		log.info("remove...." + rno);
		
		return mapper.delete(rno);
		
	}
	*/
	
	//댓글수 고려
	@Transactional
	@Override
	public int remove(Long rno) {

		log.info("remove...." + rno);
		
		//sql처리가 3개로 3개 모두 성공시에 commit이 됨
		
		ReplyVO vo = mapper.read(rno); //bno멤버변수값을 구하기 위해 사용
		
		boardMapper.updateReplyCnt(vo.getBno(), -1);

		return mapper.delete(rno);

	}

	@Override
	public List<ReplyVO> getList(Criteria cri, Long bno) {
		
		log.info("get Reply List of a Board " + bno);

		return mapper.getListWithPaging(cri, bno);
	}
	
	@Override
	public ReplyPageDTO getListPage(Criteria cri, Long bno) {

		return new ReplyPageDTO(mapper.getCountByBno(bno), mapper.getListWithPaging(cri, bno));
	}

}
