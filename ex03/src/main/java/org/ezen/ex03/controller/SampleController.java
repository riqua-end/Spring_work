package org.ezen.ex03.controller;

import org.ezen.ex03.domain.SampleVO;
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
	
	@GetMapping(value = "/getSample" ,
				produces = {MediaType.APPLICATION_JSON_VALUE,
							MediaType.APPLICATION_XML_VALUE
				})
	
	//produces 속성은 생략해도 됨
	//SampleVO객체를 반환시에 json이나 xml로 반환(기본은 xml)
	public SampleVO getSample() {
		
		return new SampleVO(112,"스타","로드");
	}
	
	@GetMapping(value = "getSample2",
				produces = {MediaType.APPLICATION_JSON_VALUE})
	//5.3.26버젼에서는 .json 요청은 동작 안하므로 produces로 조정 json으로 반환 
	public SampleVO getSample2() {
		
		return new SampleVO(113,"로켓","라쿤");
	}
}
