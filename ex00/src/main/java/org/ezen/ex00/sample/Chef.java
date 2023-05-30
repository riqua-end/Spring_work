package org.ezen.ex00.sample;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component //스프링에서 bean으로 등록하라는 어노테이션
@Data
//Data어노테이션은 equals(),canEqual(),hashCode(), toString(), Chef(),getter,setter자동 생성
public class Chef {
	
}
