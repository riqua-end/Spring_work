package org.ezen.ex02.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {
	
	private int pageNum; //페이지 번호
	private int amount; //한 페이지의 게시글 갯수
	
	public Criteria() { //controller list에 cri값이 전달 안될시 초기값
		this(1,10);
	}
	
	public Criteria(int pageNum,int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
}
