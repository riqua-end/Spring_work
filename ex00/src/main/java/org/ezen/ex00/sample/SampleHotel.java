package org.ezen.ex00.sample;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Component
@ToString //toString 메서드 만듦
@Getter
@RequiredArgsConstructor
public class SampleHotel {
	
	@NonNull //@RequiredArgsConstructor에서 사용할 멤버변수 final변수로 지정해도 됨
	//private final Chef chef;
	private Chef chef; //이 멤버변수를 이용한 생성자는 자동 주입에 사용(@Autowired없이 자동 주입)
	
	private String name;
}
