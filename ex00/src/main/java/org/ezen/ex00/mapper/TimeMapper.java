package org.ezen.ex00.mapper;

import org.apache.ibatis.annotations.Select;

public interface TimeMapper {
	
	@Select("SELECT sysdate FROM dual")
	//@Select 어노테이션은 mybatis제공 어노테이션으로 select문을 처리
	public String getTime();
	
}
