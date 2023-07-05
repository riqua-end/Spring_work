package org.ezen.ex02.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

//tbl_board테이블의 컬럼과 매핑되는 클래스(자바 빈클래스 규격으로 만들어짐)
@Data
public class BoardVO {
	
	private Long bno;  //tbl_board의 데이터형이 number(10,0)이므로 Long
	private String title; //varchar2
	private String content;
	private String writer;
	private Date regDate; //Date
	private Date updateDate;
	
	private int replyCnt; //댓글 개수
	
	private List<BoardAttachVO> attachList; //게시글 객체 1개가 갖는 첨부파일 List

}
