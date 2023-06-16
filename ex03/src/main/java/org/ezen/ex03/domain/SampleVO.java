package org.ezen.ex03.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor //모든 멤버변수를 파라메터로 갖는 생성자
@NoArgsConstructor //파라메터가 없는 생성자(기본형 생성자)
public class SampleVO {
	
	private Integer mno;
	private String firstName;
	private String lastName;
	
}
