package org.ezen.ex01.domain;

import lombok.Data;

//자바 bean 규칙을 따르는 클래스
//기본 생성자는 필히 있고 정상적인 getter,setter로 된 클래스로 Lombok으로 만들면 됨
@Data
public class SampleDTO {
	
	private String name;
	private int age;
}
