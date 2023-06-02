package org.ezen.ex01.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.log4j.Log4j;

@ControllerAdvice
//AOP의 횡단 관심사와 콘트롤러가 결합된 클래스임을 나타냄,bean으로 등록되고 콘트롤러처럼 jsp를 반환
@Log4j
public class CommonExceptionAdvice {
	
	@ExceptionHandler(Exception.class)
	//괄호란의 예외를 처리하는 예외처리 advice를 낱타내는 어노테이션(advice는 횡단 관심사 처리 코드로 스프링은 메서드)
	//@Controller형식으로 jsp반환
	public String except(Exception ex,Model model) {
		
		log.error("Exception........." + ex.getMessage());
		model.addAttribute("exception", ex); //jsp에 포함되는 속성
		log.error(model);
		return "error_page"; //jsp페이지명
	}
}
