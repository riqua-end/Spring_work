package org.ezen.ex02.security;

import org.ezen.ex02.domain.MemberVO;
import org.ezen.ex02.mapper.MemberMapper;
import org.ezen.ex02.security.domain.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
public class CustomUserDetailsService implements UserDetailsService {
	
	@Setter(onMethod_ = { @Autowired })
	private MemberMapper memberMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		log.warn("Load User By UserName : " + username); 
		//로그인 화면에서 입력하는 username--id를 말함
		
		MemberVO vo = memberMapper.read(username);
		
		log.warn("queried by member mapper: " + vo);
		
		return vo == null ? null : new CustomUser(vo);
		//vo객체를 사용하여 CustomerUser객체를 만듬
		//CustomerUser는 User를 상속했고 User는 UserDetails를 구현한 클래스이므로 즉 User객체를 반환
	}

}
