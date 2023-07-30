package org.ezen.ex02.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.ezen.ex02.domain.BoardAttachVO;
import org.ezen.ex02.domain.BoardVO;
import org.ezen.ex02.domain.Criteria;
import org.ezen.ex02.domain.PageDTO;
import org.ezen.ex02.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
//콘트롤라클래스로 스프링 빈으로 등록됨
@Log4j
@RequestMapping("/board")
//요청을 해당 메서드로 연결하는 어노테이션으로 클래스에 지정시는 공통 경로,board로 시작하는 요청은 모두 BoardController로 옴
@AllArgsConstructor
//Lombok의 모든 멤버변수를 파라메터로 갖는 생성자를 생성
//멤버변수가 하나이므로 파라메터 하나인 생성자
public class BoardController {

	private BoardService service;
	// BoardService의 메서드를 사용하기 위해서 주입받기 위한 멤버변수
	// 멤버변수가 하나인 생성자가 존재시 자동 주입되어 @Autowired생략(스프링 4.3부터)

	// 페이지 미처리
	/*
	 * @GetMapping("/list") //실제 요청 경로는 /board/list public void list(Model model) {
	 * log.info("list"); model.addAttribute("list", service.getList()); //Model객체에
	 * .addAttribute(속성명,값)로 값을 추가하면 JSP페이지에서 속성명으로 사용 // return type이 void이면
	 * mapping의 url과 동일한 이름의 jsp(board/list.jsp) }
	 */

	// 페이지 처리
	@GetMapping("/list")
	public void list(Criteria cri, Model model) {
		
		// cri를 자동 수집하므로 cri값이 없을시는 기본형 생성자가 설정하는 값(1,10)으로 수집
		log.info("cri: " + cri);
		model.addAttribute("list", service.getList(cri));
		
		//PageDTO를 반영, 게시글 총 갯수 임시 반영
		//model.addAttribute("pageMaker", new PageDTO(cri, 123)); //123은 임시로 게시글 갯수
		
		//실제 게시글 총갯수 적용
		int total = service.getTotal(cri);
		log.info("total: " + total);

		model.addAttribute("pageMaker", new PageDTO(cri, total));
		
	}

	// 등록 화면 처리,시큐리티 미적용
	/*
	@GetMapping("/register")
	public void register() {
		log.info("----registerForm");
		// return은 board/register.jsp
	}
	*/
	
	// 등록 화면 처리,시큐리티 적용(로그인한 경우에만 펠이지 이동,아니면 로그인 페이지로
	@GetMapping("/register")
	@PreAuthorize("isAuthenticated()")
	public void register() {
		log.info("----registerForm");
		// return은 board/register.jsp
	}
	
	//첨부물 미고려
	/*
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
		
		log.info("==========================");
		// RedirectAttributes는 redirect:일시 파라메터를 실어보내는 객체(form의 getParameter역활)
		log.info("register: " + board);

		service.register(board);

		rttr.addFlashAttribute("result", board.getBno());
		// board.getBno()는 bno값을 반환
		// 1회용 데이터 처리

		return "redirect:list";
		// sendRedirect()로 브라우져에서 전달하는 경로로 요청
		// return값이 redirect:나 jsp페이지 이름일시는 반환형이 String
		// sendRedirect("list?result=bno)
	}
	*/
	
	//첨부물 고려 테스트,시큐리티 미적용
	/*
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
		
		log.info("==========================");
		// RedirectAttributes는 redirect:일시 파라메터를 실어보내는 객체(form의 getParameter역활)
		
		log.info("register: " + board);
		
		if (board.getAttachList() != null) {

			board.getAttachList().forEach(attach -> log.info(attach));

		}
		
		log.info("==========================");
		
		service.register(board);

		rttr.addFlashAttribute("result", board.getBno());
		// board.getBno()는 bno값을 반환
		// 1회용 데이터 처리

		return "redirect:list";
		// sendRedirect()로 브라우져에서 전달하는 경로로 요청
		// return값이 redirect:나 jsp페이지 이름일시는 반환형이 String
		// sendRedirect("list?result=bno)
	}
	*/
	
	//첨부물 고려 테스트,시큐리티 적용
	@PostMapping("/register")
	@PreAuthorize("isAuthenticated()")
	public String register(BoardVO board, RedirectAttributes rttr) {
		
		log.info("==========================");
		// RedirectAttributes는 redirect:일시 파라메터를 실어보내는 객체(form의 getParameter역활)
		
		log.info("register: " + board);
		
		if (board.getAttachList() != null) {

			board.getAttachList().forEach(attach -> log.info(attach));

		}
		
		log.info("==========================");
		
		service.register(board);

		rttr.addFlashAttribute("result", board.getBno());
		// board.getBno()는 bno값을 반환
		// 1회용 데이터 처리

		return "redirect:list";
		// sendRedirect()로 브라우져에서 전달하는 경로로 요청
		// return값이 redirect:나 jsp페이지 이름일시는 반환형이 String
		// sendRedirect("list?result=bno)
	}
	
	// 조회처리,수정 보여주기 --페이지 미처리
	/*
	@GetMapping({ "/get", "/modify" })
	// 요청의 파라메터도 동일하고 Model에 실어주는 데이터도 동일시는 배열 형태로 Mapping
	public void get(@RequestParam("bno") Long bno, Model model) {
		log.info("get");
		// 페이지 이동은 board/get.jsp, board/modify.jsp로 이동
		model.addAttribute("board", service.get(bno));
	}
	*/
	
	//페이지 처리를 한 get,modify
	@GetMapping({ "/get", "/modify" })
	public void get(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model) {
		// bean규칙의 DTO객체는 자동 model에 포함
		// @ModelAttribute("cri")는 model에 cri속성으로 cri객체를 강제로 저장
		// 기본형을 Model에 포함시킬때
		log.info("/get or modify");
		model.addAttribute("board", service.get(bno));
	}
	

	// 게시글 수정 처리--페이지 미처리
	/*
	@PostMapping("/modify")
	public String modify(BoardVO board, RedirectAttributes rttr) {
		log.info("modify:" + board);
		if (service.modify(board)) {
			// return redirect:하는 페이지로 속성값 전달
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:list";
		// return값이 redirect:나 jsp페이지 이름일시는 반환형이 String
	}
	*/
	
	// 페이지 정보,검색처리 고려하고 시큐리티 미적용한 modify
	/*
	 @PostMapping("/modify") 
	 //public String modify(BoardVO board,Criteria cri, RedirectAttributes rttr) { 
	 public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
			log.info("modify:" + board);

			if (service.modify(board)) {
				rttr.addFlashAttribute("result", "success");
			}
			
			//rttr.addAttribute("pageNum", cri.getPageNum());
			//rttr.addAttribute("amount", cri.getAmount()); 			
			//검색처리 추가
			//rttr.addAttribute("type", cri.getType());
			//rttr.addAttribute("keyword", cri.getKeyword());
			
			//return "redirect:list";
			
			
			return "redirect:list" + cri.getListLink();
	 }
	*/
	
	// 페이지 정보,검색처리 고려하고 시큐리티 적용한 modify
	 @PostMapping("/modify") 
	 @PreAuthorize("principal.username == #board.writer") //로그인한 아이디와 게시글 ㅈ닥성한 동일 체크
	 //public String modify(BoardVO board,Criteria cri, RedirectAttributes rttr) { 
	 public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
			log.info("modify:" + board);

			if (service.modify(board)) {
				rttr.addFlashAttribute("result", "success");
			}
			
			//rttr.addAttribute("pageNum", cri.getPageNum());
			//rttr.addAttribute("amount", cri.getAmount()); 			
			//검색처리 추가
			//rttr.addAttribute("type", cri.getType());
			//rttr.addAttribute("keyword", cri.getKeyword());
			
			//return "redirect:list";
			
			
			return "redirect:list" + cri.getListLink();
	 }
	
	// 게시글 삭제 처리(post로 처리)--페이지 미처리
	 /*
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, RedirectAttributes rttr) {

		log.info("remove..." + bno);
		if (service.remove(bno)) {
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:list";
	}
	*/
	 
	// 페이지 정보 고려,검색처리,첨부 파일,댓글 삭제는 미처리
	/*
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, Criteria cri, RedirectAttributes rttr) {

		log.info("remove..." + bno);

		if (service.remove(bno)) {
			rttr.addFlashAttribute("result", "success");
		}
		
		
		//rttr.addAttribute("pageNum", cri.getPageNum());
		//rttr.addAttribute("amount", cri.getAmount()); // list로 검색조건을 넘김
		//검색처리 추가
		//rttr.addAttribute("type", cri.getType());
		//rttr.addAttribute("keyword", cri.getKeyword());
		
		//return "redirect:list";
		
		
		return "redirect:list" + cri.getListLink();
	}
	*/
	 
	//수정화면에서 게시글 삭제 처리,시큐리티는 미처리(게시글,첨부물,댓글 모두 삭제 처리)
	 /*
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, Criteria cri, RedirectAttributes rttr) {

		log.info("remove..." + bno);

		List<BoardAttachVO> attachList = service.getAttachList(bno); //첨부파일을 리스트로 반환

		if (service.remove(bno)) {

			// delete Attach Files(C:upload폴더에 저장된 파일 삭제)
			deleteFiles(attachList);

			rttr.addFlashAttribute("result", "success");
		}

		return "redirect:list" + cri.getListLink();
	}
	*/
	
	//수정화면에서 게시글 삭제 처리,시큐리티 처리(게시글,첨부물,댓글 모두 삭제 처리)
	@PostMapping("/remove")
	@PreAuthorize("principal.username == #writer")
	public String remove(@RequestParam("bno") Long bno, Criteria cri, RedirectAttributes rttr,String writer) {

		log.info("remove..." + bno);

		List<BoardAttachVO> attachList = service.getAttachList(bno); //첨부파일을 리스트로 반환

		if (service.remove(bno)) {

			// delete Attach Files(C:upload폴더에 저장된 파일 삭제)
			deleteFiles(attachList);

			rttr.addFlashAttribute("result", "success");
		}

		return "redirect:list" + cri.getListLink();
	} 
	 
	 
	//조회화면에서 첨부 파일 처리
	@GetMapping(value = "/getAttachList", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>> getAttachList(Long bno) {
		
		log.info("getAttachList " + bno);
		
		return new ResponseEntity<>(service.getAttachList(bno), HttpStatus.OK);
	}
	
	
	private void deleteFiles(List<BoardAttachVO> attachList) {
		
		if (attachList == null || attachList.size() == 0) {
			return;
		}
		
		log.info("delete attach files...................");
		log.info(attachList);
		
		attachList.forEach(attach -> {
			try {
				Path file = Paths.get(
						"C:/upload/" + attach.getUploadPath() + "/" + attach.getUuid() + "_" + attach.getFileName()); //원본파일과 일반 파일

				Files.deleteIfExists(file); //파일존재시 삭제

				if (Files.probeContentType(file).startsWith("image")) {

					Path thumbNail = Paths.get("C:/upload/" + attach.getUploadPath() + "/s_" + attach.getUuid() + "_"
							+ attach.getFileName());

					Files.delete(thumbNail);
				}

			} catch (Exception e) {
				log.error("delete file error" + e.getMessage());
			} // end catch
		});
	}
	
}
