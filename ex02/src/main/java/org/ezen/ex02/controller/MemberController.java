package org.ezen.ex02.controller;

import org.ezen.ex02.domain.MemberVO;
import org.ezen.ex02.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/member")
@Log4j
public class MemberController {
	
	@Setter(onMethod_ = @Autowired) 
	private MemberService mservice;
	
	@GetMapping("/all")
	public void doAll() {
		log.info("do all can access everybody");
	}
	
	@GetMapping("/member") // ROLE_MEMBER,ROLE_MANAGER,ROLE_ADMIN
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
		//Authentication객체는 username,password,ROLE정보(authorities)등을 가지고 있음

		model.addAttribute("msg", "Access Denied");
	}
	
	@GetMapping("/customLogin")
	public void loginInput(String error, String logout, Model model) {
		
		//error는 로그인 실패시 스프링에서 error내용을 전달하고 logout시는 로그아웃 정보를 전달
		log.info("error: " + error);

		log.info("logout: " + logout);

		if (error != null) {
			model.addAttribute("error", "Login Error -- Check Your Account");
		}

		if (logout != null) {
			model.addAttribute("logout", "Logout!!");
		}

	}
	
	@GetMapping("/customLogout")
	public void logoutGET() {		

		log.info("custom logout");
	}
	
	@GetMapping("/memberJoin")
	public void memberJoinGet() {
		log.info("Join입력창");
	}
	
	@GetMapping(value= "/idChk", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")	
	@ResponseBody
	public String memberJoinIdChk(String userid) {
		log.info("Join ID 중복체크");
		System.out.println("userid : " + userid);
		String result = mservice.joinIdCheck(userid);
		return result;
	}
	
	@PostMapping("/memberJoin")
	public String memberJoinPost(MemberVO vo) {
		System.out.println("vo : " + vo.getUserName());
		
		int result = mservice.joinRegister(vo);
		
		if(result > 0) {
			return "member/customLogin";
		}
		else {
			return "redirect:memberJoin";
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
	@GetMapping("/annoMember")
	public void doMember2() {

		log.info("logined annotation member");
	}
	
	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/annoAdmin")
	public void doAdmin2() {

		log.info("admin annotaion only");
	}
}
