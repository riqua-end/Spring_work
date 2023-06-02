package org.ezen.ex01.controller;

import java.util.ArrayList;
import java.util.Arrays;

import org.ezen.ex01.domain.SampleDTO;
import org.ezen.ex01.domain.SampleDTOList;
import org.ezen.ex01.domain.TodoDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.log4j.Log4j;

@Controller
//bean으로 등록(servlet-context.xml에서)하고 MVC의 controller로 사용
@RequestMapping("/sample")
//속성으로 지정된 요청 경로를 해당 메서드(바로 밑에 있음)나 콘트롤러클래스에서 처리
//브라우져에서 요청시 /sample경로에 있는 요청을 처리
@Log4j
//로그처리(기본 설정되어 설정은 필요없음)
public class SampleController {
	/*
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//클라이언트에서 보내는 문자열 형태를 yyyy-MM-dd형태로 보내야만 Date객체로 변환
		//format(String str)은 문자열을 Date객체로 변환
		//parse(Date d)는 date객체를 문자열로 변환
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,false));
		//문자열이 date형으로 형변환
	}
	*/
	
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
	
	// (jsp의 체크박스)
	//클라이언트에서 동일한 파라메터명으로 복수개를 보낼시는 List(구현된 클래스)로 처리
	@GetMapping("/ex02List")
	public String ex02List(@RequestParam("ids") ArrayList<String> ids) {
		
		log.info("ids: ---- " + ids);
		
		return "sample/ex02List";
	}
	
	// 복수개의 파라메터 값을 배열로 처리
	@GetMapping("/ex02Array")
	public String ex02Array(@RequestParam("ids") String[] ids) {
		
		log.info("array ids : " + Arrays.toString(ids));
		
		return "sample/ex02Array";
	}
	
	//배열이나 리스트의 요소가 문자열이 아닌 객체형일시 처리도 가능
	//보낼시에 list[0].name=k1&list[1].name=kim&list[1].age=20로 보내는데 url encode가 안돼서 에러 발생
	//[는 %5B , ]는 %5D
	//list%5B0%5D.name=k1&list%5B1%5D.name=kim&list%5B1%5D.age=20
	//컬렉션의 요소로 객체를 사용시 
	@GetMapping("/ex02Bean")
	public String ex02Bean(SampleDTOList list) {
		
		log.info("list dtos : " + list);
		
		return "sample/ex02Bean";
	}
	
	//client에서 오는 문자열을 Date형으로 자동 형변환이 안되므로 자동으로 @InitBinder의 메서드를 호출하여 형변환
	@GetMapping("/ex03")
	public String ex03(TodoDTO todo) {
		
		log.info("todo : " + todo);
		System.out.println(todo.getDueDate());
		//Fri Jun 02 00:00:00 KST 2023 로 표시되는 Date객체
		return "sample/ex02";
	}
	
	//자바 빈 규격의 객체는 이동할 jsp에 자동 포함되나 기본형은 안된다
	@GetMapping("/ex04")
	public String ex04(SampleDTO dto,int page) {
		
		log.info("dto : " + dto);
		log.info("page : " + page);
		
		return "sample/ex04";
	}
	
	//기본형을 이동할 jsp페이지에 넣기 위해서는 @ModelAttribute("전달 속성명")
	@GetMapping("/ex04_01")
	public String ex04_01(SampleDTO dto,@ModelAttribute("page") int page) {
		
		log.info("dto : " + dto);
		log.info("page : " + page);
		
		return "sample/ex04";
	}
	
	//RedirectAttributes클래스 이용하여 sendRedirect형식으로 페이지 이동
	//HttpSession객체에 1회용 속성을 보내고 폐기
	//addFlashAttribute("속성명",값);을 이용하여 저장
	@GetMapping("/redirect01")
	public String redirect01(RedirectAttributes rttr) {
		
		rttr.addFlashAttribute("name","kim");
		rttr.addFlashAttribute("age",20);
		rttr.addFlashAttribute("page",10);
		
		//전달되는 파라메터가 모두 Model에 추가되어야 함
		
		return "redirect:ex04_01"; //redirect: 키워드를 사용 이때는 jsp가 아닌 RequestMapping경로
	}
	
	//return타입이 void면 요청경로명과 동일한 이름의 jsp로 이동
	@GetMapping("/ex05")
	public void ex05() {
		log.info("/ex05.......");
		//실제로는 sample/ex05.jsp
	}
}
