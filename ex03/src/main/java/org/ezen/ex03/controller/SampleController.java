package org.ezen.ex03.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.ezen.ex03.domain.SampleVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
	
	@GetMapping(value = "/getList")
	// 디폴트인 xml 형태로 반환
	public List<SampleVO> getList() {
		
		return IntStream.range(1,10).mapToObj(i -> new SampleVO(i, i + "First", i + "Last"))
				.collect(Collectors.toList());
		//1부터 9까지 반복하여 i값을 가진 SampleVO객체를 만들어 list의 컬렉션으로 반환
	}
	
	@GetMapping(value = "/getList1",
				produces = {MediaType.APPLICATION_JSON_VALUE})
	//produces에 정의한 json배열형태로 반환
	public List<SampleVO> getList1() {
		
		return IntStream.range(1,10).mapToObj(i -> new SampleVO(i, i + "First", i + "Last"))
				.collect(Collectors.toList());
		//1부터 9까지 반복하여 i값을 가진 SampleVO객체를 만들어 list의 컬렉션으로 반환
	}
	
	@GetMapping(value = "/getMap")
	//Map으로 반환시는 xml객체형으로 반환
	public Map<String,SampleVO> getMap() {
		
		Map<String,SampleVO> map = new HashMap<>();
		map.put("First", new SampleVO(111,"그루트","주니어"));
		map.put("Second", new SampleVO(111,"그루트1","주니어1"));
		
		return map;
	}
	
	@GetMapping(value = "/getMap1",
				produces = {MediaType.APPLICATION_JSON_VALUE})
	//Map으로 반환시는 JSON객체형으로 반환
	public Map<String,SampleVO> getMap1() {
		
		Map<String,SampleVO> map = new HashMap<>();
		map.put("First", new SampleVO(111,"그루트","주니어"));
		map.put("Second", new SampleVO(111,"그루트1","주니어1"));
		
		return map;
	}
	
	@GetMapping(value = "/check", params = {"height","weight"})
	//params속성은 클라이언트에서 전달되는 파라메터 속성명이며 메서드의 파라메터로 매핑
	//클라이언트에서 오는 값은 모두 문자열이나 메서드의 기본형으로 자동 형변환
	//기본인 xml 형식으로 반환
	public ResponseEntity<SampleVO> check(Double height, Double weight) {
		
		SampleVO vo = new SampleVO(0,"" + height,"" + weight); //"" + height는 문자열로 변환 
		
		ResponseEntity<SampleVO> result = null;
		
		if (height < 150) {
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
			//status메서드는 상태를 기록하고 body는 response의 body에 값을 기록
			//HttpStatus.BAD_GATEWAY는 502에러
		}else {
			result = ResponseEntity.status(HttpStatus.OK).body(vo);
		}
		return result;
	}
	
	@GetMapping(value = "/check1", params = {"height","weight"},
				produces = {MediaType.APPLICATION_JSON_VALUE})
	//params속성은 클라이언트에서 전달되는 파라메터 속성명이며 메서드의 파라메터로 매핑
	//클라이언트에서 오는 값은 모두 문자열이나 메서드의 기본형으로 자동 형변환
	//json 형식으로 반환
	public ResponseEntity<SampleVO> check1(Double height, Double weight) {
		
		SampleVO vo = new SampleVO(0,"" + height,"" + weight); //"" + height는 문자열로 변환 
		
		ResponseEntity<SampleVO> result = null;
		
		if (height < 150) {
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
			//status메서드는 상태를 기록하고 body는 response의 body에 값을 기록
			//HttpStatus.BAD_GATEWAY는 502에러
		}else {
			result = ResponseEntity.status(HttpStatus.OK).body(vo);
		}
		return result;
	}
}
