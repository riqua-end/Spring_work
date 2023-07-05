package org.ezen.ex02.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.log4j.Log4j;

@Log4j
public class CustomNoOpPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		
		log.warn("before encode : " + rawPassword);
		
		//암호화를 안하고 그대로 암호화(encode) password로 사용
		return rawPassword.toString();
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		
		log.warn("matches : " + rawPassword + ":" + encodedPassword);
		
		//일반 패스워드와 encode한 패스워드 비교
		return rawPassword.toString().equals(encodedPassword);
	}

}
