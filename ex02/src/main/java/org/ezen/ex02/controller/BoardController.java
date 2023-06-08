package org.ezen.ex02.controller;

import org.ezen.ex02.domain.BoardVO;
import org.ezen.ex02.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
//콘트롤러클래스로 스프링 빈으로 등록됨
@Log4j
@RequestMapping("/board")
//요청을 해당 메서드로 연결하는 어노테이션으로 클래스에 지정시는 공통 경로,board로 시작하는 요청은 모두 BoardController로 옴
@AllArgsConstructor
//Lombok의 모든 멤버변수를 파라메터로 갖는 생성자를 생성
//멤버변수가 하나이므로 파라메터 하나인 생성자
public class BoardController {
	
	private BoardService service;
	//서비스의 메서드를 사용하기 위해서 주입받기 위한 멤버변수
	//멤버변수가 하나인 생성자가 존재시 자동 주입되어 @Autowired생략 (스프링 4.3부터)
	
	@GetMapping("/list") //실제 요청 경로는 board/list
	public void list(Model model) {
		log.info("list");
		model.addAttribute("list" , service.getList());
		//Model객체에 .addAttribute(속성명,값)로 값을 추가하면 jsp페이지에서 속성명으로 사용
		//return type이 void이면 mapping의 url과 동일한 이름의 jsp(board/list.jsp)
	}
	
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
		
		log.info("register : " + board);
		
		service.register(board);
		
		rttr.addFlashAttribute("result" , board.getBno());
		//1회용 데이터 처리
		
		return "redirect:/board/list";
		//sendRedirect()로 브라우저에서 전달하는 경로로 요청
		//return값이 redirect:나 jsp페이지 이름일시는 반환형이 String
	}
	
	//조회처리
	@GetMapping({"/get","/modify"})
	//요청의 파라메터도 동일하고 Model에 실어주는 데이터도 동일시는 배열 형태로 Mapping
	public void get(@RequestParam("bno") Long bno, Model model) {
		log.info("get");
		//페이지 이동은 board/get.jsp , board/modify.jsp로 이동
		model.addAttribute("board", service.get(bno));
	}
	
	//수정처리
	@PostMapping("/modify")
	public String modify(BoardVO board, RedirectAttributes rttr) {
		log.info("modify : " + board);
		if(service.modify(board)) {
			//return redirect:하는 페이지로 속성값 전달
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/board/list";
	}
	
	//삭제처리
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, RedirectAttributes rttr) {
		log.info("remove......" + bno);
		if (service.remove(bno)) {
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/board/list";
	}
}