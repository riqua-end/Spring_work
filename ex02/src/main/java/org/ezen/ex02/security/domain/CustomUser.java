package org.ezen.ex02.security.domain;

import java.util.Collection;
import java.util.stream.Collectors;

import org.ezen.ex02.domain.MemberVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

@Getter
public class CustomUser extends User{

	private static final long serialVersionUID = 1L;
	
	private MemberVO member;
	
	//User클래스는 UserDetails인터페이스를 구현한 클래스로 생성자가 파라메터 3개를 갖는 것만 존재하므로 이를 호출해주어야 함
	public CustomUser(String username, String password, 
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	public CustomUser(MemberVO vo) {
		super(vo.getUserid(), vo.getUserpw(), vo.getAuthList().stream()
				.map(auth -> new SimpleGrantedAuthority(auth.getAuth())).collect(Collectors.toList()));
		
		this.member = vo;
	}
}
