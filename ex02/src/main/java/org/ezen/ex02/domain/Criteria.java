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
	
	//검색을 위해 추가
	private String type; //검색종류 (title,writer,content) (T,W,C)
	private String keyword;
	
	public Criteria() { //controller list에 cri값이 전달 안될시 초기값
		this(1,10);
	}
	
	public Criteria(int pageNum,int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	
	//문자열 type을 배열로 변환( 글자 한자씩을 배열로)
	//BoardMapper.xml 에서 typeArr이름의 파라메터로 사용이 됨
	//MyBatis는 엄격한 빈규칙을 따르지 않고 getter,setter를 활용
	public String[] getTypeArr() {
		
		return type == null? new String[] {}: type.split("");
		//type은 문자열로 공백없이("TWC") 오는데 split하면 배열 {T,W,C}
	}
}
