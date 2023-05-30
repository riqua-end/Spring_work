package org.ezen.ex00.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Setter;

@Component //스프링에서 bean으로 등록하라는 어노테이션
@Data
public class Restaurant {
	
	//밑에 나오는 멤버변수의 set메서드를 만듦
	//onMethod_=@Autowired는 set메서드 위에 @Autowired어노테이션을 추가
	//@Autowired는 그 아래있는 멤버변수를 빈으로 주입
	//주입하므로 chef = new Chef()로 생성하지 않음
	@Setter(onMethod_=@Autowired)
	private Chef chef;
}
