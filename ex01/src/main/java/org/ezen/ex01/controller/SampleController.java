package org.ezen.ex01.controller;

import org.ezen.ex01.domain.SampleDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.log4j.Log4j;

@Controller
//bean으로 등록(servlet-context.xml에서)하고 MVC의 controller로 사용
@RequestMapping("/sample")
//속성으로 지정된 요청 경로를 해당 메서드(바로 밑에 있음)나 콘트롤러클래스에서 처리
//브라우져에서 요청시 /sample경로에 있는 요청을 처리
@Log4j
//로그처리(기본 설정되어 설정은 필요없음)
public class SampleController {
	
	@RequestMapping("")
	//메서드에 있는 @RequestMapping의 파라메타에 있는 경로로 요청오면 밑에 있는 메서드를 실행
	//실제 요청경로는 클래스의 요청경로 + 메서드의 요청 경로임(/sample)
	public String basic() {
		
		log.info("basic......"); //log는 로그처리 객체
		return "sample/basic"; //views/sample/basic.jsp로 포워딩
	}
	
	//4.3 이전 까지는 @RequestMapping사용 ,method는 안 적으면 자동으로 판단
	@RequestMapping(value = "/basic", method = { RequestMethod.GET , RequestMethod.POST })
	public void basicGet() {
		//리턴타입이 void면 요청 경로와 동일한 jsp페이지로 이동 (sample/basic.jsp)
		log.info("basic get.........");
	}
	
	//4.3 부터 method방식에 따른 전용 어노테이션 지원
	//get전용은 @GetMapping,post전용은 @PostMapping
	@GetMapping("/basicOnlyGet")
	public void basicGet2() {
		//리턴타입없이 void이면 요청 경로와 동일한 jsp페이지로 이동 (sample/basicOnlyGet.jsp)
		log.info("basic get only get.......");
	}
	
	//bean클래스로 된 객체를 직접 파라메터로 받음
	//bean의 setter를 호출해서 해당 멤버변수를 설정
	//클라이언트에서 오는 파라메터 변수명과 빈클래스의 멤버변수가 일치
	//클라이언트는 모두 문자열로 보내나 멤버변수가 기본형인 경우는 모두 자동 형변환
	@GetMapping("/ex01")
	public String ex01(SampleDTO dto) {
		
		log.info("----" + dto);
		
		//return type에 String이면 return값은 ex01.jsp
		return "sample/ex01";
	}
	
	//@RequestParam("클라이언트에서 보내는 파라메터명")은 메서드의 파라메터명을 다르게 사용시 활용
	//getParameter()대신하여 클라이언트에서 보낸값을 얻어냄
	@GetMapping("/ex02")
	public String ex02(@RequestParam("name") String name,@RequestParam("age") int age) {
		
		log.info("name:" + name);
		log.info("age: " + age);
		
		return "sample/ex02";
	}
}
