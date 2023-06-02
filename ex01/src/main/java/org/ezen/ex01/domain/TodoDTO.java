package org.ezen.ex01.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class TodoDTO {
	
	private String title;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	//문자열로 오는 파라메터값을 Date형식으로 변환
	//문자열 형식은 pattern형식이어야 함
	private Date dueDate;
	
}
