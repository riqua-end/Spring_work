package org.ezen.ex02.domain;

import lombok.Data;

@Data
//tbl_attach테이블과 매핑되는 VO클래스
public class BoardAttachVO {
	
	private String uuid;
	private String uploadPath;
	private String fileName;
	private boolean fileType;
	
	private Long bno;

}
