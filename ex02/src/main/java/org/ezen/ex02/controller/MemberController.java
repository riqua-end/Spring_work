package org.ezen.ex02.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequestMapping("/member")
public class MemberController {

	@GetMapping("/all")
	public void doAll() {
		log.info("do all can access everybody");
	}
	
	@GetMapping("/member") //ROLE_MEMBER,ROLE_MANAGER,ROLE_ADMIN
	public void doMember() {
		log.info("logined member");
	}
	
	@GetMapping("/admin")
	public void doAdmin() {
		log.info("admin only");
	}
	
	@GetMapping("/accessError")
	public void accessDenied(Authentication auth, Model model) {
		log.info("access Denied : " + auth);
		//Authentication객체는 username,password,ROLE정보(Authorities)등을 가지고 있음
		
		model.addAttribute("msg", "Access Denied");
	}
	
	@GetMapping("/customLogin")
	public void loginInput(String error, String logout, Model model) {
		
		//error는 로그인 실패 시 스프링에서 error내용을 전달하고 logout시는 로그아웃 정보를 전달
		log.info("error : " + error);
		
		log.info("logout : " + logout);
		
		if(error != null) {
			model.addAttribute("error", "Login Error -- Check Your Account");
		}
		
		if(logout != null) {
			model.addAttribute("logout", "Logout!!");
		}
	}
	
}
