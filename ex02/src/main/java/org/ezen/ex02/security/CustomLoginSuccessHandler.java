package org.ezen.ex02.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.log4j.Log4j;

@Log4j
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		log.warn("login Success");
		
		List<String> roleNames = new ArrayList<>();
		
		authentication.getAuthorities().forEach(authority -> {
			//Collection<? extends GrantedAuthority>를 getAuthorities()는 반환
			//GrantedAuthority 객체 authority를 반복 처리하여 문자열로 된 권한 정보를 getAuthority()로 얻어서 list에 저장
			
			roleNames.add(authority.getAuthority());
		});
		
		log.warn("ROLE NAMES: " + roleNames);
		
		if(roleNames.contains("ROLE_ADMIN")) {
			
			response.sendRedirect("member/admin");
			
			return;
		}
		if(roleNames.contains("ROLE_MANAGER")) {
			
			response.sendRedirect("member/member");
			
			return;
		}
		if(roleNames.contains("ROLE_MEMBER")) {
			
			response.sendRedirect("member/member");
			
			return;
		}
		
		response.sendRedirect("/ex02");
	}

}
