package org.ezen.ex02.mapper;

import org.ezen.ex02.domain.AuthVO;
import org.ezen.ex02.domain.MemberVO;

public interface MemberMapper {
	
	//member 1인의 데이터와 이와ㅣ 관련된 다수개의 권한 정보를 갖는 MemberVO객체를 반환하는 메서드
	public MemberVO read(String userid);
	
	//회원 등록
	public int memberJoin(MemberVO mVO);
	
	//권한 등록
	public int memberAuth(AuthVO aVO);

}
