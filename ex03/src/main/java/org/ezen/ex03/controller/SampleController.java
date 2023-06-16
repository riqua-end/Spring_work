package org.ezen.ex03.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j;

@RestController //REST방식의 콘트롤러,객체형 반환시는 xml이 기본
@RequestMapping("/sample") //공통경로
@Log4j //스프링의 로그 기록(정보,경고,에러 기록)
public class SampleController {
	
	@GetMapping(value = "/getText", produces = "text/plain; charset=UTF-8")
	// produces속성은 메서드가 생산하여 반환하는 MIME타입
	//순수 문자열 반환시는 리턴타입을 String
	public String getText() {
		
		log.info("MIME TYPE : " + MediaType.TEXT_PLAIN_VALUE);
		
		return "안녕하세요";
	}
	
}
