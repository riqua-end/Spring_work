package org.ezen.ex01.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SampleDTOList {
	
	private List<SampleDTO> list;
	//SampleDTO객체를 요소로 갖는 list
	
	
	//@Data가 만드는 기본형 생성자를 재정의
	public SampleDTOList() {
		
		list = new ArrayList<>();
	}
}
