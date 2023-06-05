package org.ezen.ex02.domain;

import java.sql.Date;

import lombok.Data;

//tbl_board 테이블의 컬럼과 매핑되는 클래스
@Data
public class BoardVO {
	
	private Long bno; //tbl_board 의 데이터형이 number(10,0)이므로 Long
	private String title; //varchar2
	private String content;
	private String writer;
	private Date regDate; //Date
	private Date updateDate;
	private int replyCnt;
}
